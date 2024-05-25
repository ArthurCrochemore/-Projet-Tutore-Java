package fr.univtours.polytech.punchingmachine.controller;

// Import for file reading and parsing
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
// import for time gestion
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;
import fr.univtours.polytech.punchingcommon.model.PacketPunching;
import fr.univtours.polytech.punchingmachine.MainApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
// Import for drag and drop
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class FXMLController {

    private static final String MESSAGE_READ_FILE_ERROR = "Error while reading the file";
    private static final String MESSAGE_INVALID_SOCKET_PARAMETERS = "Invalid socket parameters";
    private static final String MESSAGE_ROUNDED_HOUR = "Let's say you punched at ";

    @FXML
    private Label dateLabel;

    @FXML
    private Label hourLabel;

    @FXML
    private Label roundedHourLabel;

    @FXML
    private TextField uuidField;

    @FXML
    private TextField ipField;

    @FXML
    private TextField portField;

    @FXML
    private Label ipPunchingMachineLabel;

    private PunchingSender sender;

    private Timer timerSynchoTime;

    private LocalDate displayedDate;

    @FXML
    /**
     * Method which handle the drag and drop of a file and also the drag and drop of
     * a string
     * 
     * @param event the event of the drag and drop
     */
    public void handleDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        
        if (dragboard.hasFiles()) {
            String id = null;
            File file = null;
            if (!dragboard.getFiles().isEmpty()) {
                file = dragboard.getFiles().get(0);
            } else if (dragboard.getUrl() != null) {
                try {
                    file = new File(new URI(dragboard.getUrl()));
                } catch (URISyntaxException e) {
                    System.out.println("The uri is not valid.");
                }
            }

            if (file != null) {
                id = getValueFromFile(file, "ID");
            }

            if (id != null) {
                uuidField.setText(id);
                success = true;
            } else {
                System.out.println("The file does not contain an ID.");
            }
        } else if (dragboard.hasString()) {
            uuidField.setText(dragboard.getString());
            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }

    /**
     * Method which read a file and return the value of the key
     * 
     * @param file the file to read
     * @param key  the key to find (ex : "ID" for ID=...)
     * @return the value of the key
     */
    private String getValueFromFile(File file, String key) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine().trim()) != null) {
                if (line.startsWith(key)) {
                    int index = line.indexOf("=");
                    if (index != -1 && index + 1 < line.length()) {
                        return line.substring(index + 1).trim();
                    }
                }
            }
        } catch (IOException e) {
            showPopupError(e, MESSAGE_READ_FILE_ERROR);
        }
        return null;
    }

    @FXML
    /**
     * Method which handle the drag over of a file and also the drag over of a
     * string
     * 
     * @param event the event of the drag over
     */
    public void handleDragOver(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasFiles() || dragboard.hasString()) {
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }

    @FXML
    /**
     * Method which handle the drag exited of a file and also the drag exited of a
     * string
     * 
     * @param event the event of the drag exited
     */
    public void handleDragExited(DragEvent event) {
        event.consume();
    }

    @FXML
    /**
     * Method which handles the action of the check button
     * 
     * @param event the event of the button
     */
    public void handleButtonCheck(ActionEvent event) {
        String id = uuidField.getText();

        if (id == null || id.isEmpty()) {
            showPopupMessage("The ID field is empty.");
            return;
        }

        InetSocketAddress socketParameters = getSocketParameters();
        sender.setSocketConfigurations(socketParameters);

        // We create the packet and send it to the server
        try {
        PacketPunching packetPunching = new PacketPunching(UUID.fromString(id));
        sender.sendPacket(packetPunching);
        } catch (IllegalArgumentException e) {
            showPopupError(e, "The ID is not valid.");
        }
    }

    /**
     * Generate the socket parameters from the IP and the port fields
     * 
     * @return the socket parameters
     * @throws IllegalArgumentException if the IP address or the port are not valid
     */
    public InetSocketAddress getSocketParameters() throws IllegalArgumentException {
        int port;
        String ipStr = ipField.getText();
        String portStr = portField.getText();

        if (ipStr == null || portStr == null || ipStr.isEmpty() || portStr.isEmpty()) {
            showPopupMessage("the IP address and the port are required");
            return null;
        }

        try {
            port = Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            showPopupMessage("the port must be a valid number");
            return null;
        }

        try {
            return new InetSocketAddress(ipStr, port);
        } catch (IllegalArgumentException e) {
            showPopupError(e, MESSAGE_INVALID_SOCKET_PARAMETERS);
            return null;
        }
    }

    /**
     * Method which show a popup message
     * 
     * @param message the message to show
     */
    public static void showPopupMessage(String message) {
        // We create an alert (popup), that we show to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Method which show a popup error
     * 
     * @param e       the exception
     * @param message the message to show
     */
    public static void showPopupError(Exception e, String message) {
        // Create and show a popup
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    /**
     * Mehtod to initialize the FXML, with the time and the id
     */
    public void initialize() {
        sender = MainApp.getInstance().getPunchingSender();

        try {
            ipPunchingMachineLabel.setText("IP : " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Error while getting the IP of the punching machine : " + e.getMessage());
        }

        ipField.setText("localhost");
        portField.setText("8090");
        sender.setSocketConfigurations(getSocketParameters());

        timerSynchoTime = new Timer();
        timerSynchoTime.schedule(new TimerTask() {
            @Override
            public void run() {
                // Update the label from the JavaFX thread
                Platform.runLater(() -> updateTime());
            }
        }, 0, 1000);

        // Ask the user if he wants to fill the ID with an example
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fill the ID");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to fill the ID with the test ID ?");
        alert.showAndWait();
        if (alert.getResult().getButtonData().isDefaultButton()) {
            uuidField.setText("f9aa8da9-7b4b-4c75-9a9b-850e1dddd254");
        }
        
        // Action to do when the window is closed
        MainApp.getInstance().getStage().setOnCloseRequest(event -> onExit());
    }

    /**
     * Method to handle the exit of the application
     */
    private void onExit() {
        timerSynchoTime.cancel();
    }

    /**
     * Update the time displayed on the screen
     */
    public void updateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDate currentDate = currentDateTime.toLocalDate();
        if (!currentDate.equals(displayedDate)) {
            // If the date has changed, we update the label
            displayedDate = currentDate;
            dateLabel.setText(TimeUtils.localDateToString(currentDate));
        }

        LocalTime currentHour = currentDateTime.toLocalTime();
        hourLabel.setText(TimeUtils.localTimeToStringWithSeconds(currentHour));

        LocalTime roundedHour = TimeUtils.roundTo15Minutes(currentHour);
        roundedHourLabel.setText(MESSAGE_ROUNDED_HOUR + TimeUtils.localTimeToString(roundedHour));
    }
}
