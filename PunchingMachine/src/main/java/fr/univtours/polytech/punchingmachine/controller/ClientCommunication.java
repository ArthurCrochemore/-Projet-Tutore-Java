package fr.univtours.polytech.punchingmachine.controller;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import fr.univtours.polytech.punchingcommon.model.PacketInfoEmployee;
import fr.univtours.polytech.punchingcommon.model.PacketPunching;

/**
 * Class for the communication with the server.
 * The client send a PacketPunching and the server respond with a
 * PacketInfoEmployee.
 * It is possible to send multiple PacketPunching, to do so, we send a boolean
 * after the PacketPunching
 * with the value TRUE if we the packet sent is the last packet.
 * After the last packet, the connecition is closed by the server.
 */
public class ClientCommunication {
    // Error messages
    private static final String ERROR_MESSAGE_CONNECTION = "Error when connecting to the server : ";
    private static final String ERROR_MESSAGE_CLOSING_CONNECTION = "Error when closing the connection with the server : ";
    private static final String ERROR_MESSAGE_SENDING_PACKET = "Error when sending the packet to the server : ";
    private static final String ERROR_MESSAGE_READING_RECEIVED_OBJECT = "Error when reading the received object : ";
    private static final String ERROR_MESSAGE_READING_RECEIVED_PACKET = " Error when reading the received packet : ";

    // Success messages
    private static final String SUCCESS_MESSAGE_ALREADY_CONNECTED = "The connection is already established";
    private static final String SUCCESS_FLUX_COMMUNICATION_CREATED = "Communication stream created with the server";
    private static final String SUCCESS_MESSAGE_CONNECTION = "The connection has been created with the server";
    private static final String SUCCESS_MESSAGE_CLOSING_CONNECTION = "Connection closed with the server";

    private static final String MESSAGE_PACKET_SENT = "Packet sent to the server";
    private static final String MESSAGE_RECEIVING_PACKET = "Receiving packet from the server...";

    // Attributes to communicate with the server
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String lastErrorMessage;
    private Exception lastException;

    /**
     * Display an error in the console and register the error message
     * 
     * @param e              The exception
     * @param statusMessage  A message to say what step failed
     * @param showStackTrace Should the stacktrace be printed in the console
     */
    private void onException(Exception e, String statusMessage, boolean showStackTrace) {
        System.err.println(statusMessage + e.getMessage());
        if (showStackTrace) {
            e.printStackTrace();
        }

        lastErrorMessage = statusMessage;
        lastException = e;
    }

    /**
     * Get the last error message which discribes the step where the error occured
     * 
     * @return the last error message
     */
    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    /**
     * Get the last exception
     * 
     * @return the last exception
     */
    public Exception getLastException() {
        return lastException;
    }

    /**
     * Display an error in the console and register the error message
     * 
     * @param e             The exception
     * @param statusMessage A message to say what step failed
     */
    private void onException(Exception e, String statusMessage) {
        this.onException(e, statusMessage, true);
    }

    /**
     * Check if the client is connected to the server
     * 
     * @return true if the client is connected to the server
     */
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    /**
     * We open the connection with the server
     *
     * @param socketParameters the parameters of the connection
     */
    public boolean openConnection(InetSocketAddress socketParameters) {
        try {
            if (isConnected()) {
                System.out.println(SUCCESS_MESSAGE_ALREADY_CONNECTED);
                return true;
            }

            // We create the socket
            socket = new Socket(socketParameters.getHostName(), socketParameters.getPort());
            System.out.println(SUCCESS_MESSAGE_CONNECTION);

            // We create the output stream
            out = new ObjectOutputStream(socket.getOutputStream());

            // We create the input stream
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println(SUCCESS_FLUX_COMMUNICATION_CREATED);
            return true;
        } catch (IOException e) {
            onException(e, ERROR_MESSAGE_CONNECTION, false);
            return false;
        }
    }

    /**
     * We close the connection with the server
     */
    public void closeConnection() {
        try {
            if (out != null) {
                out.close();
                out = null;
            }
            if (in != null) {
                in.close();
                in = null;
            }
            if (socket != null) {
                socket.close();
                socket = null;
            }

            System.out.println(SUCCESS_MESSAGE_CLOSING_CONNECTION);
        } catch (IOException e) {
            onException(e, ERROR_MESSAGE_CLOSING_CONNECTION);
            socket = null;
        }
    }

    /**
     * Send a packet to the server
     *
     * @param packet     the packet to send
     * @param lastPacket is it the last packet to send in this communication
     */
    public boolean sendPacket(PacketPunching packet, boolean lastPacket) {
        try {
            out.writeObject(packet);
            out.writeBoolean(lastPacket);
            out.flush();
            System.out.println(MESSAGE_PACKET_SENT);
            return true;
        } catch (IOException e) {
            onException(e, ERROR_MESSAGE_SENDING_PACKET);
            return false;
        }
    }

    /**
     * Receiving the packet from the server
     *
     * @return the packet received or null if an error occurs
     */
    public PacketInfoEmployee receiveCheckResponse() {
        try {
            // We read the packet
            return (PacketInfoEmployee) in.readObject();
        } catch (IOException e) {
            onException(e, ERROR_MESSAGE_READING_RECEIVED_PACKET);
        } catch (ClassNotFoundException e) {
            onException(e, ERROR_MESSAGE_READING_RECEIVED_OBJECT);
        }
        return null;
    }

    /**
     * Send a packet to the server and get the response
     * 
     * @param packet     the packet to send
     * @param lastPacket is it the last packet to send
     * @return the packet received or null if an error occurs
     */
    public PacketInfoEmployee sendPacketAndGetResult(PacketPunching packet, boolean lastPacket) {
        if (!sendPacket(packet, lastPacket)) {
            return null;
        }

        // We receive the packet from the server
        System.out.println(MESSAGE_RECEIVING_PACKET);
        return receiveCheckResponse();
    }
}
