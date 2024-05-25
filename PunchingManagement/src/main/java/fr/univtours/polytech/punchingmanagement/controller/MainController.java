package fr.univtours.polytech.punchingmanagement.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import fr.univtours.polytech.punchingmanagement.MainApp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainController {

    @FXML
    private Pane body;

    public static FXMLLoader loadFXML(String resource) throws IOException {
        URL resourceUrl = Optional
                .ofNullable(MainApp.class.getResource(resource))
                .orElseThrow(() -> new IOException("Cannot load view " + resource));
        FXMLLoader loader = new FXMLLoader(resourceUrl);
        loader.load();
        return loader;
    }

    private void setBody(FXMLLoader loader) throws IOException {
        body.getChildren().setAll((Node) loader.getRoot());
    }

    public static Scene createScene(FXMLLoader loader) {
        Parent parentNode = loader.getRoot();
        Scene scene = new Scene(parentNode);
        scene.getStylesheets().add(MainApp.class.getResource("view/styles.css").toExternalForm());
        return scene;
    }

    public static Scene openWindow(FXMLLoader loader, String title, Window owner, Modality modality) {
        Scene scene;
        scene = createScene(loader);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.initModality(modality);
        stage.initOwner(owner);
        stage.show();
        return scene;
    }

    public static Scene openWindow(FXMLLoader loader, String title, Window owner) {
        return openWindow(loader, title, owner, Modality.NONE);
    }

    public static Scene openWindowModal(FXMLLoader loader, String title, Window owner) {
        return openWindow(loader, title, owner, Modality.APPLICATION_MODAL);
    }

    @FXML
    public void handleMenuItemHome() throws IOException {
        FXMLLoader loader = loadFXML("view/Home.fxml");
        HomeController controller = (HomeController) loader.getController();
        controller.setMainController(this);
        setBody(loader);
    }

    @FXML
    public void handleMenuItemConsultAll() throws IOException {
        setBody(loadFXML("view/ConsultAll.fxml"));
    }

    @FXML
    public void handleMenuItemConsultDay() throws IOException {
        setBody(loadFXML("view/ConsultDay.fxml"));
    }

    @FXML
    public void handleMenuItemEmployeeList() throws IOException {
        openWindowModal(loadFXML("view/CRUD/EmployeeList.fxml"), "Employee list", body.getScene().getWindow());
    }

    @FXML
    public void handleMenuItemDepartmentList() throws IOException {
        openWindowModal(loadFXML("view/CRUD/DepartmentList.fxml"), "Department list", body.getScene().getWindow());
    }

	@FXML
	public void handleButtonSave() throws IOException {
		SaveController.save();
	}

    @FXML
    public void handleMenuItemPunchingList() throws IOException {
        openWindowModal(loadFXML("view/CRUD/PunchingList.fxml"), "Punching list", body.getScene().getWindow());
    }

    @FXML
    public void handleMenuItemParameters() throws IOException {
        openWindowModal(loadFXML("view/Parameters.fxml"), "Parameters", body.getScene().getWindow());
    }
    
    @FXML
    public void exitApplication(ActionEvent event) {
    	Platform.exit();
    }

    public void initialize() {
        try {
            handleMenuItemHome();
        } catch (IOException e) {
            System.err.println("Cannot open home screen : " + e.getMessage());
        }
    }
}
