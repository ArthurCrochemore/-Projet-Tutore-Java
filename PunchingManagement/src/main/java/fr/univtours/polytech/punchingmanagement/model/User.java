package fr.univtours.polytech.punchingmanagement.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

	private UUID uuid;
	private String firstName;
	private String name;

	/**
	 * Constructor of User 
	 */
	public User() {
		this(null, null);
	}

	/**
	 * Constructor of User with names
	 * 
	 * @param firstName
	 * @param name
	 */
	public User(String firstName, String name) {
		this(UUID.randomUUID(), firstName, name);
	}

	/**
	 * Constructors of User with names and uuid
	 * 
	 * @param uuid
	 * @param firstName
	 * @param name
	 */
	public User(UUID uuid, String firstName, String name) {
		this.uuid = uuid;
		this.firstName = firstName;
		this.name = name;
	}

	/**
	 * Return the uuid of the User
	 * 
	 * @return uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * Modify the uuid of the User
	 * 
	 * @param uuid
	 */
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	/**
	 * Return the firstname of the User
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Modify the firstName of the User
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Return the name of the User
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Modify the name of the User
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return the names of the User
	 * 
	 * @return String 
	 */
	public String getFirstNameLastName() {
		return firstName + " " + name;
	}

	/**
	 * Serialization in reading of the User
	 * 
	 * @param stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		firstName = (String) stream.readObject();
		name = (String) stream.readObject();
		uuid = (UUID) stream.readObject();
	}

	/**
	 * Serialization in writing of User
	 * 
	 * @param stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void writeObject(java.io.ObjectOutputStream stream) throws IOException, ClassNotFoundException {
		stream.writeObject(firstName);
		stream.writeObject(name);
		stream.writeObject(uuid);
	}

	// Change this if the class has incompatible changes with previous versions
	private static final long serialVersionUID = 1L;
}