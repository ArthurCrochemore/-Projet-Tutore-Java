package fr.univtours.polytech.punchingmanagement.model;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalTime;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;

public class TheoreticalHours implements Serializable {

	private LocalTime entry;
	private LocalTime exit;

	public TheoreticalHours(LocalTime entry, LocalTime exit) {
		super();
		this.entry = entry;
		this.exit = exit;
	}

	public LocalTime getEntry() {
		return entry;
	}

	public void setEntry(LocalTime entry) {
		this.entry = entry;
	}

	public LocalTime getExit() {
		return exit;
	}

	public void setExit(LocalTime exit) {
		this.exit = exit;
	}

	public boolean isWorking() {
		return entry != null && exit != null;
	}

	// prend une date et calcule son temps théorique de travail
	public int theoreticalWorkingTime() {
		if (!isWorking()) {
			return 0;
		}
		return TimeUtils.workingTime(getEntry(), getExit());
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws IOException, ClassNotFoundException {
		stream.writeObject(entry);
		stream.writeObject(exit);
	}

	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		this.entry = (LocalTime) stream.readObject();
		this.exit = (LocalTime) stream.readObject();
	}

	// Change this if the class has incompatible changes with previous versions
	private static final long serialVersionUID = 1L;
}