package fr.univtours.polytech.punchingmanagement.controller.crud;

import java.time.LocalDate;
import java.time.LocalTime;

import fr.univtours.polytech.punchingmanagement.MainApp;
import fr.univtours.polytech.punchingmanagement.controller.TimeSpinner;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import fr.univtours.polytech.punchingmanagement.model.PunchingDay;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class PunchingCreateUpdateController extends AbstractCreateUpdateController<PunchingDay> {

    @FXML
    private ComboBox<Employee> employee;

    @FXML
    private DatePicker date;

    @FXML
    private TimeSpinner timeEntry;

    @FXML
    private TimeSpinner timeExit;

    @Override
    public void initialize() {
        super.initialize();
        employee.setItems(MainApp.getCompany().getEmployees());
        date.setValue(LocalDate.now());
        timeEntry.setValue(LocalTime.now());
        timeExit.setValue(LocalTime.now());
        employee.setOnKeyPressed(this::handleKeyPress);
        date.setOnKeyPressed(this::handleKeyPress);
        timeEntry.setOnKeyPressed(this::handleKeyPress);
        timeExit.setOnKeyPressed(this::handleKeyPress);
    }

    @Override
    public void initializeData(PunchingDay punchingDay) {
        super.initializeData(punchingDay);
        employee.getSelectionModel().select(punchingDay.getEmployee());
        date.setValue(punchingDay.getDate());
        timeEntry.setValue(punchingDay.getEntry());
        timeExit.setValue(punchingDay.getExit());
    }

    @Override
    public CRUDResources<PunchingDay> getCRUDResources() {
        return CRUDResources.getPunchingDayResources();
    }

    @Override
    protected void createItem() {
        PunchingDay punchingDay = new PunchingDay(getEmployee(),
        date.getValue(),
        timeEntry.getValue(),
        timeExit.getValue());

        getEmployee().addPunching(punchingDay);
    }

    @Override
    protected void updateItem(PunchingDay item) {
        item.setEmployee(getEmployee());
        item.setDate(date.getValue());
        item.setEntry(timeEntry.getValue());
        item.setExit(timeExit.getValue());
    }

    /**
     * Gets the employee of the punching day
     * 
     * @return the employee
     */
    public Employee getEmployee() {
        return employee.getSelectionModel().getSelectedItem();
    }
}
