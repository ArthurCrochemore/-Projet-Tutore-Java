package fr.univtours.polytech.punchingmanagement.controller.crud;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;
import fr.univtours.polytech.punchingmanagement.controller.crud.CRUDResources.CRUDAction;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import fr.univtours.polytech.punchingmanagement.model.PunchingDay;
import fr.univtours.polytech.punchingmanagement.model.TheoreticalHours;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PunchingReadController extends AbstractReadController<PunchingDay> {
    private static final String SCHEDULE_NOT_WORKING = "Not working";
    private static final String SCHEDULE_WORKING = "Working from %s to %s";

    @FXML
    private Button employee;

    @FXML
    private Label date;

    @FXML
    private Label timeEntry;

    @FXML
    private Label timeExit;

    @FXML
    private Label schedule;

    @Override
    public void initialize() {
        super.initialize();
        employee.setOnKeyPressed(this::handleKeyPress);
        employee.setOnAction(event -> openEmployee());
    }

    @Override
    public void initializeData(PunchingDay punchingDay) {
        super.initializeData(punchingDay);
        Employee emp = punchingDay.getEmployee();
        TheoreticalHours th = punchingDay.getTheoreticalHours();
        if (emp != null) {
            employee.setText(emp.getFirstNameLastName());
        } else {
            employee.setText("null");
        }
        date.setText(TimeUtils.format(punchingDay.getDate()));
        timeEntry.setText(TimeUtils.format(punchingDay.getEntry()));
        timeExit.setText(TimeUtils.format(punchingDay.getExit()));

        if (th != null && th.isWorking())
            schedule.setText(String.format(SCHEDULE_WORKING,
                    TimeUtils.format(th.getEntry()), TimeUtils.format(th.getExit())));
        else
            schedule.setText(SCHEDULE_NOT_WORKING);
    }

    @Override
    public CRUDResources<PunchingDay> getCRUDResources() {
        return CRUDResources.getPunchingDayResources();
    }

    /**
     * Open the READ dialog for the employee
     */
    public void openEmployee() {
        Employee emp = getItem().getEmployee();
        if (emp != null) {
            openCRUDDialog(CRUDResources.getEmployeeResources(), CRUDAction.READ, emp);
        }
    }
}
