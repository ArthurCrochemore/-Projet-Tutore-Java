package fr.univtours.polytech.punchingcommon.model;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import java.io.Serializable;

/**
 * Class which contains the informations of a check / punch.
 * A PacketPunching is sent to the server
 * with all the informations needed for the check in/out
 */
public class PacketPunching implements Serializable {
    private UUID uuid;
    private LocalTime roundedTime;
    private LocalDate date;

    /**
     * Constructor for the PacketPunching
     * The time is rounded to 15 minutes
     * 
     * @param uuidEmployee the id of the employee
     */
    public PacketPunching(UUID uuidEmployee) {
        uuid = uuidEmployee;
        roundedTime = TimeUtils.getCurrentRoundedLocalTime();
        date = LocalDate.now();
    }

    /**
     * Getter for the id of the employee
     * 
     * @return the id of the employee
     */
    public UUID getUUIDEmployee() {
        return uuid;
    }

    /**
     * Getter for the rounded hour of the punching
     * 
     * @return the hour rounded to 15 minutes
     */
    public LocalTime getRoundedTime() {
        return roundedTime;
    }

    /**
     * Getter of the date of the punching
     * 
     * @return the date of the punching
     */
    public LocalDate getDate() {
        return date;
    }
}
