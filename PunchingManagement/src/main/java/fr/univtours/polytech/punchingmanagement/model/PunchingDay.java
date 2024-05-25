package fr.univtours.polytech.punchingmanagement.model;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;
import fr.univtours.polytech.punchingmanagement.MainApp;

public class PunchingDay implements Serializable {

	private UUID employeeUUID;
	private LocalDate date;
	private LocalTime entry;
	private LocalTime exit;

	/**
	 * Constructor of PunchingDay
	 */
	public PunchingDay() {
		this((Employee) null);
	}

	/**
	 * Constructor of PunchingDay for an Employee in parameter
	 * 
	 * @param employee
	 */
	public PunchingDay(Employee employee) {
		this(employee, TimeUtils.getCurrentRoundedLocalDateTime());
	}

	/**
	 * Constructor of PunchingDay for an Employee and a date in parameter
	 * 
	 * @param employee
	 * @param dateTime
	 */
	public PunchingDay(Employee employee, LocalDateTime dateTime) {
		this(employee, dateTime.toLocalDate(), dateTime.toLocalTime(), null);
	}

	/**
	 * Constructor of PunchingDay for an Employee in parameter, a date and an entry in parameter
	 * 
	 * @param employee
	 * @param date
	 * @param entry
	 */
	public PunchingDay(Employee employee, LocalDate date, LocalTime entry) {
		this(employee, date, entry, null);
	}

	/**
	 * Constructor of PunchingDay for an Employee in parameter, a date, an entry and an exit in parameter
	 * 
	 * @param employee
	 * @param date
	 * @param entry
	 * @param exit
	 */
	public PunchingDay(Employee employee, LocalDate date, LocalTime entry, LocalTime exit) {
		this.employeeUUID = employee != null ? employee.getUuid() : null;
		this.date = date;
		this.entry = entry;
		this.exit = exit;
	}

	/**
	 * Return the attribute date
	 * 
	 * @return date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Modify the attribute date
	 * 
	 * @param date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Return the attribute entry
	 * 
	 * @return entry
	 */
	public LocalTime getEntry() {
		return entry;
	}

	/**
	 * Modify the attribute entry
	 * 
	 * @param entry
	 */
	public void setEntry(LocalTime entry) {
		this.entry = entry;
	}

	/**
	 * Return the attribute exit
	 * 
	 * @return exit
	 */
	public LocalTime getExit() {
		return exit;
	}

	/**
	 * Modify the attribute exit
	 * 
	 * @param exit
	 */
	public void setExit(LocalTime exit) {
		this.exit = exit;
		if (employeeUUID != null) {
			Employee employee = getEmployee();
			if(employee != null) {
				employee.addWorkingTimeToHourlyRate(this);
			}
		}
	}

	/**
	 * Modify the attribute employeeUUID
	 * 
	 * @param employeeUUID
	 */
	public void setEmployeeUUID(UUID employeeUUID) {
		this.employeeUUID = employeeUUID;
	}

	/**
	 * Modify the attribute employeeUUID
	 * 
	 * @return employeeUUID
	 */
	public UUID getEmployeeUUID() {
		return employeeUUID;
	}

	/**
	 * Return the employee of UUID employeeUUID
	 * 
	 * @return EMployee
	 */
	public Employee getEmployee() {
		return MainApp.getCompany().getEmployee(employeeUUID);
	}

	/**
	 * Modify the employeeUUID with an Employee in parameter
	 * 
	 * @param employee
	 */
	public void setEmployee(Employee employee) {
		this.employeeUUID = employee.getUuid();
	}

	/**
	 * Check if the employee has punch his exit
	 * 
	 * @return boolean
	 */
	public boolean hasPunchedTwice() {
		return this.exit != null;
	}

	/**
	 * Return the TheoreticalHours for the attribute date
	 * 
	 * @return TheoreticalHours
	 */
	public TheoreticalHours getTheoreticalHours() {
		return getEmployee().getWeeklySchedule().getTheoreticalHours(date);
	}

	/**
	 * Return the time worked of the Employee
	 * 
	 * @return int
	 */
	public int getWorkedTime() {
		return TimeUtils.workingTime(entry, exit);
	}

	/**
	 * Serialization in reading of PunchingDay
	 * 
	 * @param stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		this.employeeUUID = (UUID) stream.readObject();
		this.date = (LocalDate) stream.readObject();
		this.entry = (LocalTime) stream.readObject();
		this.exit = (LocalTime) stream.readObject();
	}

	/**
	 * Serialization in writing of PunchingDay
	 * 
	 * @param stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void writeObject(java.io.ObjectOutputStream stream) throws IOException, ClassNotFoundException {
		stream.writeObject(this.employeeUUID);
		stream.writeObject(this.date);
		stream.writeObject(this.entry);
		stream.writeObject(this.exit);
	}

	// Change this if the class has incompatible changes with previous versions
	private static final long serialVersionUID = 1L;
}