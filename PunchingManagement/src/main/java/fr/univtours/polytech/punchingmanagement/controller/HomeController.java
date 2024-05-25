package fr.univtours.polytech.punchingmanagement.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class HomeController {

	@FXML
	private Pane root;

	private MainController mainController;

	@FXML
	public void handleButtonConsultAll() throws IOException {
		mainController.handleMenuItemConsultAll();
	}

	@FXML
	public void handleButtonConsultDay() throws IOException {
		mainController.handleMenuItemConsultDay();
	}

	@FXML
	public void handleButtonEmployeeList() throws IOException {
		mainController.handleMenuItemEmployeeList();
	}

	@FXML
	public void handleButtonDepartmentList() throws IOException {
		mainController.handleMenuItemDepartmentList();
	}

	@FXML
	public void handleButtonPunchingList() throws IOException {
		mainController.handleMenuItemPunchingList();
	}

	protected void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public void initialize() {
		// Nothing to do
	}
}
