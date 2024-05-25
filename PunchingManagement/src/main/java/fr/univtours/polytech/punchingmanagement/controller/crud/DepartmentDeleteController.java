package fr.univtours.polytech.punchingmanagement.controller.crud;

import fr.univtours.polytech.punchingmanagement.MainApp;
import fr.univtours.polytech.punchingmanagement.model.Department;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DepartmentDeleteController extends AbstractDeleteController<Department> {

    @FXML
    private Label uuid;

    @FXML
    private Label name;

    @FXML
    private Label numberOfEmployees;

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

    @Override
    protected void deleteItem() {
        MainApp.getCompany().getDepartments().remove(getItem());
    }
}
