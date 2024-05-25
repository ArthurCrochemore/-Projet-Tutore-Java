package fr.univtours.polytech.punchingmanagement.controller.crud;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import fr.univtours.polytech.punchingmanagement.model.PunchingDay;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PunchingDeleteController extends AbstractDeleteController<PunchingDay> {

    @FXML
    private Label employee;

    @FXML
    private Label date;

    @FXML
    private Label timeEntry;

    @FXML
    private Label timeExit;

    @Override
    public void initializeData(PunchingDay punchingDay) {
        super.initializeData(punchingDay);
        Employee emp = punchingDay.getEmployee();
        if (emp != null)
            employee.setText(emp.getFirstNameLastName());
        else
            employee.setText("null");
        date.setText(TimeUtils.format(punchingDay.getDate()));
        timeEntry.setText(TimeUtils.format(punchingDay.getEntry()));
        timeExit.setText(TimeUtils.format(punchingDay.getExit()));
    }

    @Override
    public CRUDResources<PunchingDay> getCRUDResources() {
        return CRUDResources.getPunchingDayResources();
    }

    @Override
    protected void deleteItem() {
        PunchingDay punchingDay = getItem();
        punchingDay.getEmployee().removePunching(punchingDay.getDate());
    }
}
