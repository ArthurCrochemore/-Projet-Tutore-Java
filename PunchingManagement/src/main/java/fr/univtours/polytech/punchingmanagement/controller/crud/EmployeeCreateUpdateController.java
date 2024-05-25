package fr.univtours.polytech.punchingmanagement.controller.crud;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;
import fr.univtours.polytech.punchingmanagement.MainApp;
import fr.univtours.polytech.punchingmanagement.controller.TimeSpinner;
import fr.univtours.polytech.punchingmanagement.model.Department;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import fr.univtours.polytech.punchingmanagement.model.TheoreticalHours;
import fr.univtours.polytech.punchingmanagement.model.WeeklySchedule;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class EmployeeCreateUpdateController extends AbstractCreateUpdateController<Employee> {
	List<TimeSpinner> startTimes;
	List<TimeSpinner> endTimes;

	@FXML
	private GridPane gridSchedule;

	@FXML
	private Label uuid;

	@FXML
	private TextField firstname;

	@FXML
	private TextField lastname;

	@FXML
	private DatePicker dateOfEmployment;

	@FXML
	private ComboBox<Department> department;

	@Override
	public void initialize() {
		super.initialize();

		uuid.setText(String.valueOf(UUID.randomUUID()));
		dateOfEmployment.setValue(LocalDate.now());
		department.setItems(MainApp.getCompany().getDepartments());
		firstname.setOnKeyPressed(this::handleKeyPress);
		lastname.setOnKeyPressed(this::handleKeyPress);
		dateOfEmployment.setOnKeyPressed(this::handleKeyPress);
		department.setOnKeyPressed(this::handleKeyPress);

		startTimes = new ArrayList<>();
		endTimes = new ArrayList<>();
		for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
			TimeSpinner start = new TimeSpinner(null);
			TimeSpinner end = new TimeSpinner(null);
			int index = dayOfWeek.getValue() - 1;
			gridSchedule.add(start, 3, index);
			gridSchedule.add(end, 5, index);
			startTimes.add(index, start);
			endTimes.add(index, end);
			start.setEditable(true);
			end.setEditable(true);
			start.setPrefWidth(80);
			end.setPrefWidth(80);
		}
	}

	@Override
	public void initializeData(Employee employee) {
		super.initializeData(employee);

		uuid.setText(String.valueOf(employee.getUuid()));
		firstname.setText(employee.getFirstName());
		lastname.setText(employee.getName());
		dateOfEmployment.setValue(employee.getEmploymentDate());
		department.getSelectionModel().select(employee.getDepartment());

		WeeklySchedule weeklySchedule = employee.getWeeklySchedule();

		for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
			initializeDaySchedyle(weeklySchedule, dayOfWeek);
		}
	}

	@Override
	public CRUDResources<Employee> getCRUDResources() {
		return CRUDResources.getEmployeeResources();
	}

	/**
	 * Set the time of the two TimeSpinner with the schedule of the day
	 * 
	 * @param weeklySchedule the schedule
	 * @param dayOfWeek      the day of week
	 */
	private void initializeDaySchedyle(WeeklySchedule weeklySchedule, DayOfWeek dayOfWeek) {
		int index = dayOfWeek.getValue() - 1;
		TheoreticalHours day = weeklySchedule.getTheoreticalHours(dayOfWeek);
		TimeSpinner start = startTimes.get(index);
		TimeSpinner end = endTimes.get(index);
		if (day != null) {
			start.setValue(day.getEntry());
			end.setValue(day.getExit());
		} else {
			start.setValue(null);
			end.setValue(null);
		}
	}

	/**
	 * Set the theoretical hours of the day from the two TimeSpinner
	 * 
	 * @param schedule the schedule
	 * @param day      the day of the week
	 * @param start    the start time
	 * @param end      the end time
	 */
	private void setTheoreticalHours(WeeklySchedule schedule, DayOfWeek day, TimeSpinner start, TimeSpinner end) {
		LocalTime startTime = TimeUtils.roundTo15Minutes(start.getValue());
		LocalTime endTime = TimeUtils.roundTo15Minutes(end.getValue());
		if (startTime != null && endTime != null && (startTime.isBefore(endTime) || startTime.equals(endTime))) {
			schedule.addTheoreticalHours(day, new TheoreticalHours(startTime, endTime));
		} else {
			schedule.addTheoreticalHours(day, new TheoreticalHours(null, null));
		}
	}

	@Override
	protected void createItem() {
		Employee employee = new Employee(UUID.fromString(uuid.getText()), firstname.getText(), lastname.getText(),
				dateOfEmployment.getValue());
		employee.setDepartment(department.getSelectionModel().getSelectedItem());

		WeeklySchedule weeklySchedule = employee.getWeeklySchedule();
		for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
			int index = dayOfWeek.getValue() - 1;
			setTheoreticalHours(weeklySchedule, dayOfWeek, startTimes.get(index), endTimes.get(index));
		}

		MainApp.getCompany().getEmployees().add(employee);
	}

	@Override
	protected void updateItem(Employee item) {
		item.setFirstName(firstname.getText());
		item.setName(lastname.getText());
		item.setEmploymentDate(dateOfEmployment.getValue());
		item.setDepartment(department.getSelectionModel().getSelectedItem());

		WeeklySchedule weeklySchedule = item.getWeeklySchedule();
		for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
			int index = dayOfWeek.getValue() - 1;
			setTheoreticalHours(weeklySchedule, dayOfWeek, startTimes.get(index), endTimes.get(index));
		}
	}
}
