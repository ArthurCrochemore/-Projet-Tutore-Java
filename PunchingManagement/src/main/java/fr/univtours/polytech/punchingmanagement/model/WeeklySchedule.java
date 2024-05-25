package fr.univtours.polytech.punchingmanagement.model;

import java.io.IOException;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WeeklySchedule implements Serializable {

	private UUID employeeUUID;
	private Map<DayOfWeek, TheoreticalHours> schedule;

	/**
	 * Constructor of WeeklySchedule with an uuid
	 * 
	 * @param uuid
	 */
	public WeeklySchedule(UUID uuid) {
		super();
		this.employeeUUID = uuid;
		this.schedule = new HashMap<>();
	}

	/**
	 * Modify the schedule with a new one in parameter
	 * 
	 * @param schedule
	 */
	public void setSchedule(Map<DayOfWeek, TheoreticalHours> schedule) {
		this.schedule = schedule;
	}

	/**
	 * Return the attribute employeeUUID
	 * 
	 * @return employeeUUID
	 */
	public UUID getUUID() {
		return employeeUUID;
	}

	/**
	 * Modify the attribute employeeUUID
	 * 
	 * @param uuid
	 */
	public void setUUID(UUID uuid) {
		this.employeeUUID = uuid;
	}

	/**
	 * Add a new theoreticalHours in parameter to a DayOfWeek in parameter
	 * 
	 * @param dayOfWeek
	 * @param theoreticalHours
	 */
	public void addTheoreticalHours(DayOfWeek dayOfWeek, TheoreticalHours theoreticalHours) {
		schedule.put(dayOfWeek, theoreticalHours);
	}

	/**
	 * Return the TheoreticalHours for the date in parameter
	 * 
	 * @param date
	 * @return TheoreticalHours
	 */
	public TheoreticalHours getTheoreticalHours(LocalDate date) {
		return schedule.get(date.getDayOfWeek());
	}

	/**
	 * Return the TheoreticalHours for the day in parameter
	 * 
	 * @param day
	 * @return TheoreticalHours
	 */
	public TheoreticalHours getTheoreticalHours(DayOfWeek day) {
		return schedule.get(day);
	}

	/**
	 * Serialization in writing of WeeklySchedule
	 * 
	 * @param stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void writeObject(java.io.ObjectOutputStream stream) throws IOException, ClassNotFoundException {
		for (DayOfWeek day : DayOfWeek.values())
			stream.writeObject(schedule.get(day));
	}

	/**
	 * Serialization in reading of WeeklySchedule
	 * 
	 * @param stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		schedule = new HashMap<>();
		for (DayOfWeek day : DayOfWeek.values()) {
			TheoreticalHours newHours = (TheoreticalHours) stream.readObject();
			schedule.put(day, newHours);
		}
	}

	// Change this if the class has incompatible changes with previous versions
	private static final long serialVersionUID = 1L;
}