package fr.univtours.polytech.punchingcommon.model;

/**
 * An enum for the state of the check.
 * A StateCheck is sent to the punching machine with the PacketInfoEmployee
 */
public enum StateCheck {
    /**
     * The Check failed
     */
    CHECK_UNKNOW,
    
    /**
     * Check in successful
     */
    CHECK_IN,
    
    /**
     * Check out successful
     */
    CHECK_OUT,

    /**
     * The employee has finished his work day
     */
    CHECK_EXHAUSTED,

    /**
     * The check out another day than the check in
     */
    ERROR_CHECK_OUT,

    /**
     * Unknown employee
     */
    UNKNOWN_EMPLOYEE
}