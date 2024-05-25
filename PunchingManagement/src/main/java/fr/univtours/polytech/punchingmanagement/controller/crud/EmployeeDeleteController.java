package fr.univtours.polytech.punchingmanagement.controller.crud;

import fr.univtours.polytech.punchingmanagement.MainApp;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EmployeeDeleteController extends AbstractDeleteController<Employee> {

    @FXML
    private Label uuid;

    @FXML
    private Label firstname;

    @FXML
    private Label lastname;

    @Override
    public void initializeData(Employee employee) {
        super.initializeData(employee);
        uuid.setText(String.valueOf(employee.getUuid()));
        firstname.setText(employee.getFirstName());
        lastname.setText(employee.getName());
    }

    @Override
    public CRUDResources<Employee> getCRUDResources() {
        return CRUDResources.getEmployeeResources();
    }

    @Override
    protected void deleteItem() {
        MainApp.getCompany().getEmployees().remove(getItem());
    }
}
