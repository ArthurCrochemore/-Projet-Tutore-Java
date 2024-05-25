package fr.univtours.polytech.punchingmanagement.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Company implements Serializable {

	private ObservableList<Department> departments;
	private ObservableList<Employee> employees;
	private ObservableList<PunchingDay> history;

	/**
	 * Constructor of Company
	 */
	public Company() {

		this.departments = FXCollections.observableArrayList();
		this.employees = FXCollections.observableArrayList();
		this.history = FXCollections.observableArrayList();
	}

	/**
	 * Return the attribute departments of Company
	 * 
	 * @return ObservableList<Department> departments of Company
	 */
	public ObservableList<Department> getDepartments() {
		return departments;
	}

	/**
	 * Add a department to the Company
	 * 
	 * @param department
	 */
	public void addDepartment(Department department) {
		departments.add(department);
	}

	/**
	 * Remove a department to Company
	 * 
	 * @param department
	 */
	public void removeDepartment(Department department) {
		departments.remove(department);
	}

	/**
	 * Return the object Department from his uuid in parameter
	 * 
	 * @param departmentUUID, uuid of the Department
	 * @return Department, the Department find
	 */
	public Department getDepartment(UUID departmentUUID) {
		return departments.stream().filter(d -> d.getUuid().equals(departmentUUID)).findFirst().orElse(null);
	}

	/**
	 * Return the attribute employees of Company
	 * 
	 * @return ObservableList<Employee> employees of Company
	 */
	public ObservableList<Employee> getEmployees() {
		return employees;
	}

	/**
	 * Return the attribute history of Company
	 * 
	 * @return ObservableList<PunchingDay> history of Company
	 */
	public ObservableList<PunchingDay> getPunchingDays() {
		return history;
	}

	/**
	 * Add an employee to employees
	 * 
	 * @param employee, Employee to add
	 */
	public void addEmployee(Employee employee) {
		employees.add(employee);
	}

	/**
	 * Check if an employee exist from his uuid in parameter
	 * 
	 * @param uuid of the employee to find
	 * @return boolean
	 */
	public boolean doesEmployeeExist(UUID uuid) {
		return employees.stream().anyMatch(employee -> employee.getUuid().equals(uuid));
	}

	/**
	 * Return an employee from his uuid in parameter
	 * 
	 * @param uuid of the employee to find
	 * @return Employee the employee found, null if not found
	 */
	public Employee getEmployee(UUID uuid) {
		return employees.stream().filter(employee -> employee.getUuid().equals(uuid)).findFirst()
				.orElse(null);
	}

	/**
	 * Serialization in reading of Company
	 * 
	 * @param stream of the .ser file open
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		departments = FXCollections.observableArrayList();
		employees = FXCollections.observableArrayList();
		history = FXCollections.observableArrayList();
		int indiceMax;

		indiceMax = stream.readInt();
		for (int indice = 0; indice < indiceMax; indice++) {
			departments.add((Department) stream.readObject());
		}

		indiceMax = stream.readInt();
		for (int indice = 0; indice < indiceMax; indice++) {
			employees.add((Employee) stream.readObject());
		}

		indiceMax = stream.readInt();
		for (int indice = 0; indice < indiceMax; indice++) {
			history.add((PunchingDay) stream.readObject());
		}
	}

	/**
	 * Serialization in writing of Company
	 * 
	 * @param stream of the .ser file open
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void writeObject(java.io.ObjectOutputStream stream) throws IOException, ClassNotFoundException {
		stream.writeInt(departments.size());
		for (Department department : departments) {
			stream.writeObject(department);
		}

		stream.writeInt(employees.size());
		for (Employee employee : employees) {
			stream.writeObject(employee);
		}

		stream.writeInt(history.size());
		for (PunchingDay punchingDay : history) {
			stream.writeObject(punchingDay);
		}
	}

	// Change this if the class has incompatible changes with previous versions
	private static final long serialVersionUID = 1L;
}