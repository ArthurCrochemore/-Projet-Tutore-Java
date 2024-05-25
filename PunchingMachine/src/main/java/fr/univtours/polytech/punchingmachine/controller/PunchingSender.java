package fr.univtours.polytech.punchingmachine.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import fr.univtours.polytech.punchingcommon.model.PacketInfoEmployee;
import fr.univtours.polytech.punchingcommon.model.PacketPunching;

/**
 * This class is used to send the packets to the server when possible.
 * If the connection is not available, the packets are saved in a file and sent later.
 * The file is saved every 15 minutes and loaded when the application is started.
 * A packet "not sent" is a packet that was not sent directly to the server.
 * When we want to send a new packet, PunchindSender send all the packets not sent before.
 * Only the information of the new packet is displayed to the user.
 */
public class PunchingSender {

    private InetSocketAddress socketConfiguration;
    private List<PacketPunching> packetsToSend;
    private ClientCommunication clientCommunication;
    private Timer sendTimer;

    // File to save the packets when the connection is not available
    private static final String FILE_PACKETS_TO_SEND = "packetsToSend.ser";
    // Time constants
    private static final int TIME_BETWEEN_CONNECTION_ATTEMPTS = 15 * 60 * 1000; // 15 minutes

    // Serialization messages
    private static final String MESSAGE_SERIALIZATION_SUCCES = "%d packets not sent were saved in %s";
    private static final String MESSAGE_SERIALIZATION_ERROR = "Error when saving %d packets not sent in %s : ";
    private static final String MESSAGE_DESERIALIZATION_SUCCES = "%d packets not sent were loaded from %s";
    private static final String MESSAGE_DESERIALIZATION_ERROR = "Error when deserializing the packets from %s : ";
    
    // Connection messages
    private static final String MESSAGE_TRYING_TO_SEND_PACKETS = "Trying to send the packets not sent to the server at ";
    private static final String MESSAGE_CONNECTION_ERROR = "Connection not available, the packets will be sent later";
    private static final String MESSAGE_RECEIVED = "Message received from the server: ";
    private static final String MESSAGE_WILL_BE_SENT_LATER = "The packet will be sent later (%d packets not sent)";
    private static final String MESSAGE_NUMBER_PACKETS_SENT = "%d packets were sent to the server, %d packets not sent remaining";

    /**
     * Class to send the packets to the server when possible
     */
    public PunchingSender() {
        this.packetsToSend = new ArrayList<>();
        this.clientCommunication = new ClientCommunication();
        // Load the packets not sent
        deserializePackets();
        startSendLaterTimer();
    }

    /**
     * Setter for the socket configuration
     * 
     * @param socketConfiguration the socket configuration to reach the server
     */
    public void setSocketConfigurations(InetSocketAddress socketConfiguration) {
        this.socketConfiguration = socketConfiguration;
    }

    /**
     * Try to send the packet and show the result to the user.
     * If there is packets not sent, they are sent before the new packet.
     * If the connection is not available, a message is shown to the user and the packet is saved for later.
     */
    public void sendPacket(PacketPunching packet) {
        PacketInfoEmployee packetInfoEmployee = sendPacketAndGetResult(packet);

        if (packetInfoEmployee == null) {
            // We add the packet to the list of packets to send later
            packetsToSend.add(packet);

            String message = String.format(MESSAGE_WILL_BE_SENT_LATER, packetsToSend.size());
            FXMLController.showPopupError(
                    clientCommunication.getLastException(),
                    clientCommunication.getLastErrorMessage() + "\n(" + message + ")");

        } else {
            // We get the message to show to the user
            String receivedMessage = packetInfoEmployee.getMessage();
            System.out.println(MESSAGE_RECEIVED + receivedMessage);

            // We show the message received from the server to the user
            FXMLController.showPopupMessage(receivedMessage);
        }
    }

    /**
     * Send a packet and return the result
     * If there is packets not sent, they are sent before the new packet.
     * 
     * @param packet the packet to send
     * @return the result received from the server, null if the connection is not available
     */
    private PacketInfoEmployee sendPacketAndGetResult(PacketPunching packet) {
        if (!sendPackets())
            return null;

        // If there is no remaining packets, we send the packet and we show the result
        // to the user

        if (!openConnection())
            return null;

        PacketInfoEmployee packetInfoEmployee = clientCommunication.sendPacketAndGetResult(packet, true);

        // We close the connection with the server
        clientCommunication.closeConnection();

        return packetInfoEmployee;
    }

    /**
     * Open the connection with the server
     * 
     * @return true if the connection is available, false otherwise
     */
    private boolean openConnection() {
        return clientCommunication.openConnection(socketConfiguration);
    }

    /**
     * Send the packets not sent to the server
     * 
     * @return true if there is no remaining packets, false otherwise
     */
    public boolean sendPackets() {
        if (packetsToSend.isEmpty()) {
            return true;
        }

        System.out.println(MESSAGE_TRYING_TO_SEND_PACKETS + socketConfiguration);

        if (!openConnection()) {
            System.out.println(MESSAGE_CONNECTION_ERROR);
            return false;
        }

        int packetsSent = 0;
        for (PacketPunching packet : new ArrayList<>(packetsToSend)) {
            boolean lastPacket = packetsToSend.size() == 1;
            PacketInfoEmployee packetInfoEmployee = clientCommunication.sendPacketAndGetResult(packet, lastPacket);
            if (packetInfoEmployee == null) {
                break;
            }
            packetsToSend.remove(packet);
            packetsSent++;

            // We get the message to show to the user
            String receivedMessage = packetInfoEmployee.getMessage();
            System.out.println(MESSAGE_RECEIVED + receivedMessage);

            // We don't show the message received, because it can be from 1 hour ago
        }
        if (packetsSent > 0) {
            System.out.println(String.format(MESSAGE_NUMBER_PACKETS_SENT, packetsSent, packetsToSend.size()));
        }

        // We close the connection with the server
        clientCommunication.closeConnection();

        return packetsToSend.isEmpty();
    }

    /**
     * Serializes the packets to a file
     */
    public void serializePackets() {
        try (ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(FILE_PACKETS_TO_SEND))) {
            fileOut.writeObject(packetsToSend);
            System.out.println(String.format(MESSAGE_SERIALIZATION_SUCCES, packetsToSend.size(), FILE_PACKETS_TO_SEND));
        } catch (IOException e) {
            String message = String.format(MESSAGE_SERIALIZATION_ERROR, packetsToSend.size(), FILE_PACKETS_TO_SEND);
            System.out.println(message + e.getMessage());
            e.printStackTrace();
            FXMLController.showPopupError(e, message);
        }
    }

    /**
     * Deserializes the packets from a file
     */
    public void deserializePackets() {
        try (ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream(FILE_PACKETS_TO_SEND))) {
            // We know that the file contains a list of PacketPunching
            List<?> list = (ArrayList<?>) fileIn.readObject();
            packetsToSend = new ArrayList<>();
            for (Object o : list) {
                if (o instanceof PacketPunching) {
                    packetsToSend.add((PacketPunching) o);
                }
            }

            System.out.println(String.format(MESSAGE_DESERIALIZATION_SUCCES, packetsToSend.size(), FILE_PACKETS_TO_SEND));
            
        } catch(FileNotFoundException e) {
            // The file does not exist, we don't do anything
        } catch (IOException | ClassNotFoundException e) {
            String message = String.format(MESSAGE_DESERIALIZATION_ERROR, FILE_PACKETS_TO_SEND);
            System.out.println(message + e.getMessage());
            e.printStackTrace();
            FXMLController.showPopupError(e, message);
        }
    }

    /**
     * Starts a timer to send packets in 15 minutes
     */
    public void startSendLaterTimer() {
        sendTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // Try to send the packets
                sendPackets();
            }
        };
        // Schedule the task to run in 15 minutes
        // Wait 1 second before starting (to get the socket configuration)
        sendTimer.schedule(task, 1000, TIME_BETWEEN_CONNECTION_ATTEMPTS);
    }

    /**
     * Called when the application is closed.
     * Saves the packets not sent to a file and stop the timer to send them.
     */
    public void onExit() {
        // We cancel the timer
        if (sendTimer != null) {
            sendTimer.cancel();
        }
        // We save the packets
        serializePackets();
    }
}
