package fr.univtours.polytech.punchingmachine;

import fr.univtours.polytech.punchingmachine.controller.PunchingSender;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static MainApp instance = null;
    private Stage stage;
    private PunchingSender punchingSender;
    
    /**
     * Start the application with the specified window
     * 
     * @param stage the main window
     */
    @Override
    public void start(Stage stage) throws Exception {
        if (instance != null) {
            throw new IllegalStateException("MainApp already initialized");
        }
        instance = this;
        this.stage = stage;
        this.punchingSender = new PunchingSender();

        Parent root = FXMLLoader.load(getClass().getResource("view/scene.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("view/styles.css").toExternalForm());

        stage.setTitle("Punching Machine");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Start the applicaiton with JavaFX
     * 
     * @param args Arguments of the application
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Event triggered when the application is stopping
     */
    @Override
    public void stop() {
        punchingSender.onExit();
    }

    /**
     * Getter for the instance of MainApp
     * 
     * @return Instance of the Singleton MainApp
     */
    public static MainApp getInstance() {
        return instance;
    }

    /**
     * Getter for the main window
     * 
     * @return The Stage (Window) of the application
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Getter for the PunchingSender
     * 
     * @return an instance of PunchingSender
     */
    public PunchingSender getPunchingSender() {
        return punchingSender;
    }
}
