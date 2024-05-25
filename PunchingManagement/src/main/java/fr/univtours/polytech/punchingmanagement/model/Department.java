package fr.univtours.polytech.punchingmanagement.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import fr.univtours.polytech.punchingmanagement.MainApp;
import javafx.collections.transformation.FilteredList;

public class Department implements Serializable {

	private String name;
	private UUID uuid;

	public Department(String name) {
		this(name, UUID.randomUUID());
	}

	public Department(String name, UUID uuid) {
		this.name = name;
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public void addEmployee(Employee employee) {
		employee.setDepartment(this);
	}

	public void removeEmployee(Employee employee) {
		employee.setDepartment(null);
	}

	public FilteredList<Employee> getEmployees() {
		return MainApp.getCompany().getEmployees().filtered(employee -> uuid.equals(employee.getDepartmentUUID()));
	}

	public int getNumberOfEmployees() {
		return getEmployees().size();
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Department) || uuid == null)
			return false;
		return uuid.equals(((Department) other).uuid);
	}

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}

	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		this.name = (String) stream.readObject();
		this.uuid = (UUID) stream.readObject();
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws IOException, ClassNotFoundException {
		stream.writeObject(this.name);
		stream.writeObject(this.uuid);
	}

    // Change this if the class has incompatible changes with previous versions
	private static final long serialVersionUID = 1L;
}