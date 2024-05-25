package fr.univtours.polytech.punchingcommon.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Class which contains the information of the employee.
 * A PacketInfoEmployee is sent by the server as an answer of the check of an employee.
 */
public class PacketInfoEmployee implements Serializable {

    // Constants for the message of the check
    private static final String MESSAGE_CHECK_IN = "Hi %s youre check in is done.\nHave a good day!";
    private static final String MESSAGE_CHECK_OUT = "Goodbye %s! You've ended your workday.\nSee you next time!";
    private static final String MESSAGE_ERROR_CHECK_OUT = "Error: You check out another day than the check in.";
    private static final String MESSAGE_CHECK_EXHAUSTED = "You have finished your work day %s\nYou can't check again.";
    private static final String MESSAGE_CHECK_UNKNOW = "Error: the check in or the check out has not been done.";
    private static final String MESSAGE_EMPLOYEE_UNKNOW = "Error: the id of the employee is not valid.";
    private static final String MESSAGE_STATE_CHECK_INVALID = "Error: the state of the check is not valid : ";


    // Attributes
    private String firstName;
    private String lastName;
    private UUID employeeUUID;
    LocalDate dateOfTheCheck;
    LocalTime timeCheck;

    private StateCheck stateCheck;

    /**
     * Constructor for the PacketInfoEmployee
     * 
     * @param firstName        the first name of the employee
     * @param lastName         the last name of the employee
     * @param employeeUUID     the employee UUID
     * @param stateCheck       the state of the check day, default is CHECK_UNKNOW
     * @param timeCheck        the time of the check
     * @param dateOfTheCheck   the date of the check
     */
    public PacketInfoEmployee(String firstName, String lastName, UUID employeeUUID, StateCheck stateCheck,
            LocalTime timeCheck, LocalDate dateOfTheCheck) {
        if (firstName == null || lastName == null || firstName.isEmpty() || lastName.isEmpty()) {
            throw new IllegalArgumentException("The first name or the last name is empty");
        }
        if (employeeUUID == null) {
            throw new IllegalArgumentException("The employee UUID is empty");
        }
        if (dateOfTheCheck == null) {
            throw new IllegalArgumentException("The date of the check is null");
        }
        if (timeCheck == null) {
            throw new IllegalArgumentException("The time of the check is null");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeUUID = employeeUUID;
        this.stateCheck = stateCheck;
        this.timeCheck = timeCheck;
        this.dateOfTheCheck = dateOfTheCheck;
    }

    /**
     * Getter for the date of the check
     * 
     * @return the date of the check
     */
    public LocalDate getDateOfTheCheck() {
        return dateOfTheCheck;
    }

    /**
     * Setter for the date of the check
     * 
     * @param dateOfTheCheck the date of the check
     */
    public void setDateOfTheCheck(LocalDate dateOfTheCheck) {
        this.dateOfTheCheck = dateOfTheCheck;
    }

    /**
     * Getter for the time of the check
     * 
     * @return the time of the check
     */
    public LocalTime getTimeCheck() {
        return timeCheck;
    }

    /**
     * Setter for the time of the check
     * 
     * @param timeCheck the time of the check
     */
    public void setTimeCheck(LocalTime timeCheck) {
        this.timeCheck = timeCheck;
    }

    /**
     * Getter for the first name of the employee
     * 
     * @return the first name of the employee
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the first name of the employee
     * 
     * @param firstName the first name of the employee
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for the last name of the employee
     * 
     * @return the last name of the employee
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for the last name of the employee
     * 
     * @param lastName the last name of the employee
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for the employee UUID
     * 
     * @return the employee UUID
     */
    public UUID getEmployeeUUID() {
        return employeeUUID;
    }

    /**
     * Setter for the employee UUID
     * 
     * @param employeeUUID the employee UUID
     */
    public void setEmployeeUUID(UUID employeeUUID) {
        this.employeeUUID = employeeUUID;
    }
    
    /**
     * Getter for the state of the check
     * 
     * @return the state of the check
     */
    public StateCheck getStateCheck() {
        return stateCheck;
    }

    /**
     * Setter for the state of the check
     * 
     * @param stateCheck the state of the check
     */
    public void setStateCheck(StateCheck stateCheck) throws IllegalArgumentException {
        this.stateCheck = stateCheck;
    }

    /**
     * Getter for the message of the check
     * 
     * @return A message about the result of the check
     */
    public String getMessage() {
        switch(stateCheck) {
            case CHECK_UNKNOW:
                return MESSAGE_CHECK_UNKNOW;
            case CHECK_IN:
                return String.format(MESSAGE_CHECK_IN, firstName + " " + lastName);
            case CHECK_OUT:
                return String.format(MESSAGE_CHECK_OUT, firstName + " " + lastName);
            case ERROR_CHECK_OUT:
                return String.format(MESSAGE_ERROR_CHECK_OUT);
            case CHECK_EXHAUSTED:
                return String.format(MESSAGE_CHECK_EXHAUSTED, firstName + " " + lastName);
            case UNKNOWN_EMPLOYEE:
                return String.format(MESSAGE_EMPLOYEE_UNKNOW);
            default:
                System.err.println(MESSAGE_STATE_CHECK_INVALID + stateCheck);
                return MESSAGE_CHECK_UNKNOW;
        }
    }
}
