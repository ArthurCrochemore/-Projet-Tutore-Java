package fr.univtours.polytech.punchingmanagement.controller.crud;

import java.util.UUID;

import fr.univtours.polytech.punchingmanagement.MainApp;
import fr.univtours.polytech.punchingmanagement.model.Department;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentCreateUpdateController extends AbstractCreateUpdateController<Department> {

    @FXML
    private Label uuid;

    @FXML
    private TextField name;

    @Override
    public void initialize() {
        super.initialize();
        uuid.setText(String.valueOf(UUID.randomUUID()));
        name.setOnKeyPressed(this::handleKeyPress);
    }

    @Override
    public void initializeData(Department employee) {
        super.initializeData(employee);
        uuid.setText(String.valueOf(employee.getUuid()));
        name.setText(employee.getName());
    }

    @Override
    public CRUDResources<Department> getCRUDResources() {
        return CRUDResources.getDepartmentResources();
    }

    @Override
    protected void createItem() {
        Department department = new Department(
                name.getText(),
                UUID.fromString(uuid.getText()));
        MainApp.getCompany().getDepartments().add(department);
    }

    @Override
    protected void updateItem(Department item) {
        item.setName(name.getText());
    }
}
