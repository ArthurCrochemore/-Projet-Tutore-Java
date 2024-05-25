package fr.univtours.polytech.punchingmanagement;

import fr.univtours.polytech.punchingmanagement.controller.FakeDataGenerator;
import fr.univtours.polytech.punchingmanagement.controller.MainController;
import fr.univtours.polytech.punchingmanagement.controller.SaveController;
import fr.univtours.polytech.punchingmanagement.controller.communication.ServeurCommunication;
import fr.univtours.polytech.punchingmanagement.model.Company;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
	private static MainApp instance = null;
	private static Company company = null;
	private ServeurCommunication server = null;

	@Override
	public void start(Stage stage) throws Exception {
		if (instance != null) {
			throw new IllegalStateException("MainApp already initialized");
		}

		instance = this;
		company = new Company();
		SaveController.load();
		if (company.getDepartments().size() == 0 && company.getEmployees().size() == 0) {
			new FakeDataGenerator().askToFillWithFakeData();
		}
		server = new ServeurCommunication();
		server.start();

		try {
			FXMLLoader loader = MainController.loadFXML("view/MainView.fxml");
			Scene scene = MainController.createScene(loader);
			stage.setTitle("Punching Management");
			stage.setScene(scene);
			stage.show();

			SaveController.startAutoBackup();
		} catch (Exception e) {
			System.err.println("Error while loading main window : " + e.getMessage());
			System.exit(1);
		}
	}

	@Override
	public void stop() {
		SaveController.stopAutoBackup();
		SaveController.save();
		server.stop();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static MainApp getInstance() {
		return instance;
	}

	public static Company getCompany() {
		return company;
	}

	public static void setCompany(Company company) {
		MainApp.company = company;
	}

	public static ServeurCommunication getServer() {
		return instance.server;
	}
}
