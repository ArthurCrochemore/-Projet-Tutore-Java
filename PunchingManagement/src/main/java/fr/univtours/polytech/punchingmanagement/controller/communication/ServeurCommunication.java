package fr.univtours.polytech.punchingmanagement.controller.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import fr.univtours.polytech.punchingcommon.model.PacketInfoEmployee;
import fr.univtours.polytech.punchingcommon.model.PacketPunching;
import fr.univtours.polytech.punchingcommon.model.StateCheck;
import fr.univtours.polytech.punchingmanagement.MainApp;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import fr.univtours.polytech.punchingmanagement.model.PunchingDay;

/**
 * A class for the communications with the client.
 * The server receives a PacketPunching and answers with a PacketInfoEmployee.
 * A boolean is sent after the PacketPunching for the server to know if it's the lastPacket.
 * If it's the lastPacket, the server stop the connection, otherwise it waits for the next PacketPunching.
 */
public class ServeurCommunication {

    private ServerSocket serverSocket;
    private int port;
    private Thread threadConnection;
    private boolean isRunning = false;

    // Error messages
    private static final String ERROR_MESSAGE_RECEIVE = "Error when receiving the packet from client : ";
    private static final String ERROR_MESSAGE_COMMUNICATION = "Error when communicating with client : ";
    private static final String ERROR_MESSAGE_NOT_STARTED = "Error when starting the server : ";
    private static final String ERROR_MESSAGE_STOPPING_SERVER = "Error when stopping the server : ";
    private static final Object EXCEPTION_SOCKET_CLOSED = "Socket closed";

    // Status messages
    private static final String MESSAGE_SERVER_STARTED = "Server started, listening on port %d";
    private static final String MESSAGE_SERVER_STOPPED = "Server stopped";
    private static final String MESSAGE_WAINTING_CONNECTIONS = "Waiting for connections...";
    private static final String MESSAGE_CONNECTED = "Client connected : ";

    private static final int DEFAULT_PORT = 8090;
    
    /**
     * Creates a server with the default port
     */
    public ServeurCommunication() {
        port = DEFAULT_PORT;
    }

    /**
     * Get the IP of the server
     * 
     * @return the IP of the server
     * @throws UnknownHostException if the IP is unknown
     */
    public String getIp() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        return localHost.getHostAddress();
    }

    /**
     * Get the port of the server
     * 
     * @return the port of the server
     */
    public int getPort() {
        return port;
    }

    /**
     * Indicates if the server is open
     * 
     * @return true if the server is open, false otherwise
     */
    public boolean isOpen() {
        return serverSocket != null && !serverSocket.isClosed();
    }

    /**
     * Start the server and start a thread to wait for connections
     */
    public void start() {
        try {
            // Create the server socket to listen on the port
            isRunning = true;
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(0); // 0 = wait for socket connection infinitely
            System.out.println(String.format(MESSAGE_SERVER_STARTED, serverSocket.getLocalPort()));

            if (threadConnection != null && threadConnection.isAlive()) {
                threadConnection.interrupt();
            }
            threadConnection = new Thread(this::waitConnection);
            threadConnection.start();
        } catch (IOException e) {
            System.out.println(ERROR_MESSAGE_NOT_STARTED + e.getMessage());
            isRunning = false;
        }
    }

    /**
     * Wait for a connection and treat the packets received
     */
    private void waitConnection() {
        System.out.println(MESSAGE_WAINTING_CONNECTIONS);

        while (isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                clientSocket.setSoTimeout(10000); // 10 seconds with the client max
                System.out.println(MESSAGE_CONNECTED
                        + clientSocket.getInetAddress().getHostAddress());
                prcessPackets(clientSocket);
                clientSocket.close();
            } catch (SocketTimeoutException e) {
                // No connection, we try again
            } catch (IOException e) {
                if (e.getMessage().equals(EXCEPTION_SOCKET_CLOSED)) {
                    // The server is closed, we stop the thread
                    break;
                }
                System.out.println(ERROR_MESSAGE_COMMUNICATION + e.getMessage());
            }
        }
    }

    /**
     * Stop the server and stop the thread waiting for connections
     */
    public void stop() {
        isRunning = false;
        if (threadConnection != null && threadConnection.isAlive()) {
            threadConnection.interrupt();
            threadConnection = null;
        }
        try {
            if (serverSocket != null) {
                serverSocket.close();
                System.out.println(MESSAGE_SERVER_STOPPED);
            }
        } catch (IOException e) {
            System.out.println(ERROR_MESSAGE_STOPPING_SERVER + e.getMessage());
        }
    }

    /**
     * Process the packets received from the client
     */
    public void prcessPackets(Socket clientSocket) {
        // We create the input and output streams
        try (ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {

            boolean lastPacket;
            do {
                // receive the packet from the client
                objectOutputStream.flush();
                PacketPunching packet = (PacketPunching) objectInputStream.readObject();
                lastPacket = objectInputStream.readBoolean();

                // read the packet
                UUID id = packet.getUUIDEmployee();
                LocalDate datePacketPunching = packet.getDate();
                LocalTime roundedTime = packet.getRoundedTime();

                // create a new packet to send to the client
                PacketInfoEmployee packetInfoEmployee;
                if (MainApp.getCompany().doesEmployeeExist(id)) {
                    Employee employee = MainApp.getCompany().getEmployee(id);
                    packetInfoEmployee = processCheck(employee, roundedTime, datePacketPunching);
                } else {
                    packetInfoEmployee = new PacketInfoEmployee(
                            "?",
                            "?", id, StateCheck.UNKNOWN_EMPLOYEE,
                            roundedTime,
                            datePacketPunching);
                }
                
                // we send the packet to the client
                objectOutputStream.writeObject(packetInfoEmployee);
                objectOutputStream.flush();
            } while (!lastPacket);
        } catch (ClassNotFoundException e) {
            System.out.println(ERROR_MESSAGE_RECEIVE + e.getMessage());
        } catch (IOException e) {
            System.out.println(ERROR_MESSAGE_COMMUNICATION + e.getMessage());
        }
    }

    /**
     * Set the parameters of the server
     *
     * @param port the port of the server
     */
    public void setParams(int port) {
        this.port = port;
    }

    /**
     * Process the check of an employee.
     * Verify if the employee is already checked in or checked out.
     * Create the packet to send to the client.
     *
     * @param employee the employee to check
     * @param checkTime the time of the check
     * @param date the date of the check
     * @return the packet to send to the client
     */
    private PacketInfoEmployee processCheck(Employee employee, LocalTime checkTime, LocalDate date) {
        PacketInfoEmployee packetInfoEmployee = new PacketInfoEmployee(
                employee.getFirstName(),
                employee.getName(), employee.getUuid(),
                StateCheck.CHECK_UNKNOW,
                checkTime,
                date);

        // if we are not already checked in and we are not already checked out

        PunchingDay punching = employee.getPunching(date);
        boolean isAlreadyCheckedIn = false;
        boolean isAlreadyCheckedOut = false;
        if (punching != null) {
            isAlreadyCheckedIn = punching.getEntry() != null;
            isAlreadyCheckedOut = punching.getExit() != null;
        } else {
            punching = new PunchingDay(employee, date, checkTime);
            employee.addPunching(punching);
        }

        if (!isAlreadyCheckedIn) {
            if (!isAlreadyCheckedOut) {
                // First check in
                packetInfoEmployee.setStateCheck(StateCheck.CHECK_IN);
                punching.setEntry(checkTime);
            } else {
                // Error : We are already checked out before checking in
                packetInfoEmployee.setStateCheck(StateCheck.ERROR_CHECK_OUT);
            }
        } else {
            // we are already checked in

            if (!isAlreadyCheckedOut) {
                // Check out
                packetInfoEmployee.setStateCheck(StateCheck.CHECK_OUT);
                punching.setExit(checkTime);
            } else {
                // Error : We are already checked in and checked out
                packetInfoEmployee.setStateCheck(StateCheck.CHECK_EXHAUSTED);
            }
        }

        return packetInfoEmployee;
    }
}
