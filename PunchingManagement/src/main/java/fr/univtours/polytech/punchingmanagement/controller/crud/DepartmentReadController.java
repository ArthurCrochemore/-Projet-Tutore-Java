package fr.univtours.polytech.punchingmanagement.controller.crud;

import fr.univtours.polytech.punchingmanagement.model.Department;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DepartmentReadController extends AbstractReadController<Department> {

    @FXML
    private Button uuid;

    @FXML
    private Label name;

    @FXML
    private Label numberOfEmployees;

    @Override
    public void initialize() {
        super.initialize();
        uuid.setOnKeyPressed(this::handleKeyPress);
        uuid.setOnAction(event -> copyToClipboard(uuid.getText()));
    }

    @Override
    public void initializeData(Department department) {
        super.initializeData(department);
        uuid.setText(String.valueOf(department.getUuid()));
        name.setText(department.getName());
        numberOfEmployees.setText(Integer.toString(department.getNumberOfEmployees()));
    }

    @Override
    public CRUDResources<Department> getCRUDResources() {
        return CRUDResources.getDepartmentResources();
    }
}
