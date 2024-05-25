package fr.univtours.polytech.punchingmanagement.controller.communication;

import java.net.UnknownHostException;

import fr.univtours.polytech.punchingmanagement.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * A controller for the server / parameter view
 */
public class ServeurController {
    private ServeurCommunication server;
    private static final String STATUS_RUNNING = "Server is running on port ";
    private static final String STATUS_NOT_RUNNING = "Server is not running";
    private static final String STATUS_RESTARTING = "Restarting Server";
    private static final String STATUS_STOP = "Server stopped";
    private static final String STATUS_NULL = "Server is null, please restart the application";
    private static final String PORT_NAN = "Port must be a number";
    private static final String POPUP_INFORMATION_TITLE = "Information";
    private static final String IP_ERROR = "Unknown Host";

    @FXML
    private Label ipServeurLabel;

    @FXML
    private TextField portServeurLabel;

    @FXML
    private Label statusLabel;

    @FXML
    public void handleStartButtonAction(ActionEvent event) throws NumberFormatException, UnknownHostException {
        startServer();
    }
    
    @FXML
    public void handleRestartButtonAction(ActionEvent event) throws NumberFormatException, UnknownHostException
    {
        restartServer();
    }
    
    @FXML
    public void handleStopButtonAction(ActionEvent event) {
        stopServer();
    }
    
    @FXML
    private void initialize() {
        server = MainApp.getServer();
        if(server == null)
        {
            showPopupMessage(STATUS_NULL);
            return;
        }
        try {
            ipServeurLabel.setText(server.getIp());
        } catch (UnknownHostException e) {
            ipServeurLabel.setText(IP_ERROR);
        }
        portServeurLabel.setText(String.valueOf(server.getPort()));
        portServeurLabel.setOnKeyPressed(this::handleKeyPress);
        if(server.isOpen())
            setStatusLabel(STATUS_RUNNING + server.getPort());
        else
            setStatusLabel(STATUS_NOT_RUNNING);
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            closeView();
        }
    }

    /**
     * Closes the view
     */
    public void closeView() {
        Stage sb = (Stage) portServeurLabel.getScene().getWindow();
        sb.close();
    }

    /**
     * Starts the server and display a status message
     * 
     * @throws NumberFormatException if the port is not valid
     * @throws UnknownHostException  if the host is unknown
     */
    private void startServer() throws NumberFormatException, UnknownHostException {
        if (server.isOpen()) // Si le serveur est dejà lance, on le ferme
        {
            server.stop();
        }
        String portStr = portServeurLabel.getText();
        int port;
        try {
            port = Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            showPopupMessage(PORT_NAN);
            return;
        }

        server.setParams(port);
        server.start();
        showPopupMessage(STATUS_RUNNING + server.getPort());
    }
    
    /**
     * Restarts the server
     * 
     * @throws NumberFormatException if the port is not valid
     * @throws UnknownHostException  if the host is unknown
     */
    private void restartServer() throws NumberFormatException, UnknownHostException
    {
        setStatusLabel(STATUS_RESTARTING);
        if (server.isOpen()) // Si le serveur est dejà lance, on le ferme
        {
            server.stop();
        }
        startServer();
    }
    
    /**
     * Stops the server and display a status message
     */
    private void stopServer() {
        server.stop();
        showPopupMessage(STATUS_STOP);
    }
    
    /**
     * Shows a popup message and display it in the status label
     */
    private void showPopupMessage(String string) 
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(POPUP_INFORMATION_TITLE);
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
        setStatusLabel(string);
    }

    /**
     * Sets the status label
     */
    private void setStatusLabel(String string) {
        statusLabel.setText(string);
    }
}
