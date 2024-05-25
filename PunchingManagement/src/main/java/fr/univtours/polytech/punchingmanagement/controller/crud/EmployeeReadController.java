package fr.univtours.polytech.punchingmanagement.controller.crud;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;
import fr.univtours.polytech.punchingmanagement.controller.crud.CRUDResources.CRUDAction;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class EmployeeReadController extends AbstractReadController<Employee> {

    @FXML
    private Button uuid;

    @FXML
    private Label firstname;

    @FXML
    private Label lastname;

    @FXML
    private Label dateOfEmployment;

    @FXML
    private Button department;

    @FXML
    private Label hoursWorked;

    @FXML
    private Label hourCredit;

    @Override
    public void initialize() {
        super.initialize();
        uuid.setOnKeyPressed(this::handleKeyPress);
        department.setOnKeyPressed(this::handleKeyPress);
        uuid.setOnAction(event -> copyToClipboard(uuid.getText()));
        department.setOnAction(event -> openDepartment());
    }

    @Override
    public void initializeData(Employee employee) {
        super.initializeData(employee);
        uuid.setText(String.valueOf(employee.getUuid()));
        firstname.setText(employee.getFirstName());
        lastname.setText(employee.getName());
        dateOfEmployment.setText(TimeUtils.format(employee.getEmploymentDate()));
        department.setText(String.valueOf(employee.getDepartment()));
        
        int timeWorked = employee.getWorkedTime();
        int timeCredit = employee.getHourlyRate();
        String timeCreditPrefix = timeCredit < 0 ? "-" : "+";
        timeCredit = Math.abs(timeCredit);

        hoursWorked.setText((timeWorked / 60) + "h" + (timeWorked % 60));
        hourCredit.setText(timeCreditPrefix + (timeCredit / 60) + "h" + (timeCredit % 60));
    }

    @Override
    public CRUDResources<Employee> getCRUDResources() {
        return CRUDResources.getEmployeeResources();
    }

    /**
     * Open the READ dialog for the department
     */
    private void openDepartment() {
        openCRUDDialog(CRUDResources.getDepartmentResources(), CRUDAction.READ, getItem().getDepartment());
    }
}
