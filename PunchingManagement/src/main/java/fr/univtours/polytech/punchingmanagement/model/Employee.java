package fr.univtours.polytech.punchingmanagement.model;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import fr.univtours.polytech.punchingmanagement.MainApp;
import javafx.collections.transformation.FilteredList;

public class Employee extends User {

	private LocalDate employmentDate;
	private WeeklySchedule weeklySchedule;
	private UUID departmentUUID;
	private int hourlyRate; // taux horaire en minutes
	private LocalDate resetTimePunchingSchedule;

	public Employee(String firstName, String name) {
		this(UUID.randomUUID(), firstName, name, LocalDate.now());
	}

	public Employee(String firstName, String name, LocalDate employmentDate) {
		this(UUID.randomUUID(), firstName, name, employmentDate);
	}

	public Employee(UUID uuid, String firstName, String name, LocalDate employmentDate) {
		super(uuid, firstName, name);
		this.employmentDate = employmentDate;
		this.hourlyRate = 0;
		this.weeklySchedule = new WeeklySchedule(uuid);
		this.resetTimePunchingSchedule = employmentDate;
	}

	public LocalDate getEmploymentDate() {
		return employmentDate;
	}

	public void setEmploymentDate(LocalDate employmentDate) {
		this.employmentDate = employmentDate;
	}

	public void addPunching(PunchingDay punchingDay) {
		punchingDay.setEmployee(this);
		MainApp.getCompany().getPunchingDays().add(punchingDay);
		addWorkingTimeToHourlyRate(punchingDay);
	}

	public void addPunching(LocalDateTime dateTimeEntry) {
		MainApp.getCompany().getPunchingDays().add(new PunchingDay(this, dateTimeEntry));
	}

	public PunchingDay getPunching(LocalDate date) {
		return getPunchingHistory().stream().filter(punchingDay -> punchingDay.getDate().equals(date)).findFirst()
				.orElse(null);
	}

	public PunchingDay getTodayPunching() {
		return getPunching(LocalDate.now());
	}

	public FilteredList<PunchingDay> getPunchingHistory() {
		return MainApp.getCompany().getPunchingDays()
				.filtered(punchingDay -> punchingDay.getEmployeeUUID().equals(getUuid()));
	}

	public void removePunching(LocalDate date) {
		MainApp.getCompany().getPunchingDays().remove(getPunching(date));
	}

	public WeeklySchedule getWeeklySchedule() {
		return weeklySchedule;
	}

	public void setWeeklySchedule(WeeklySchedule weeklySchedule) {
		this.weeklySchedule = weeklySchedule;
	}

	public UUID getDepartmentUUID() {
		return departmentUUID;
	}

	public void setDepartmentUUID(UUID departmentUUID) {
		this.departmentUUID = departmentUUID;
	}

	public Department getDepartment() {
		return MainApp.getCompany().getDepartment(departmentUUID);
	}

	public void setDepartment(Department department) {
		if (department != null)
			this.departmentUUID = department.getUuid();
		else
			this.departmentUUID = null;
	}

	public String toString() {
		return "ID : " + getUuid() + "\n" + "Prénom : " + getName() + "\n" + "Nom : " + getFirstName();
	}

	public int getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(int hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	// print HourlyHour (int) as a number of hours and minutes
	public void showHourlyRate() {
		int time = getHourlyRate();

		System.out.println("Hour = " + time / 60 + "\n Minutes" + time % 60);
	}

	public void addTimeToHourlyRate(int minuteToAdd) {
		this.hourlyRate = this.hourlyRate + minuteToAdd;
	}

	// prend une date et calcule son temps théorique de travail
	public int theoreticalWorkingTime(LocalDate date) {
		TheoreticalHours theoreticalHours = weeklySchedule.getTheoreticalHours(date);
		if (theoreticalHours == null)
			return 0;
		return theoreticalHours.theoreticalWorkingTime();
	}

	/**
	 * ajoute le temps travaillé dans son taux horaire
	 */
	public void addWorkingTimeToHourlyRate(PunchingDay punching) {

		int workedTime = 0;

		// récupère le temps travaillé
		workedTime = punching.getWorkedTime();

		// récupèration du temps théorique de travail
		int theoreticalTime = theoreticalWorkingTime(punching.getDate());

		addTimeToHourlyRate(workedTime - theoreticalTime); // ajoute le temps au HourlyRate
	}

	public int getWorkedTime() {
		int workedTime = 0;
		for (PunchingDay dayParam : getPunchingHistory()) {
			workedTime += dayParam.getWorkedTime();
		}
		return workedTime;
	}

	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		this.employmentDate = (LocalDate) stream.readObject();
		this.departmentUUID = (UUID) stream.readObject();
		this.hourlyRate = stream.readInt();
		this.weeklySchedule = (WeeklySchedule) stream.readObject();
		this.resetTimePunchingSchedule = (LocalDate) stream.readObject();
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws IOException, ClassNotFoundException {
		stream.writeObject(this.employmentDate);
		stream.writeObject(this.departmentUUID);
		stream.writeInt(this.hourlyRate);
		stream.writeObject(this.weeklySchedule);
		stream.writeObject(this.resetTimePunchingSchedule);
	}

	// Change this if the class has incompatible changes with previous versions
	private static final long serialVersionUID = 1L;
}