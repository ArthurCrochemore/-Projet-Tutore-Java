package fr.univtours.polytech.punchingmanagement;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import fr.univtours.polytech.punchingmanagement.model.TheoreticalHours;

public class TestTheoreticalHours {

    private TheoreticalHours theoreticalHours;
    private LocalTime entry;
    private LocalTime exit;

    @Before
    public void setUp() {
    	 entry = LocalTime.of(9, 0);
         exit = LocalTime.of(18, 0);
        theoreticalHours = new TheoreticalHours(entry, exit);
       
    }

    @Test
    public void testGetEntry() {
        theoreticalHours.setEntry(entry);
        assertEquals(entry, theoreticalHours.getEntry());
    }

    @Test
    public void testSetEntry() {
        theoreticalHours.setEntry(entry);
        assertEquals(entry, theoreticalHours.getEntry());
    }

    @Test
    public void testGetExit() {
        theoreticalHours.setExit(exit);
        assertEquals(exit, theoreticalHours.getExit());
    }

    @Test
    public void testSetExit() {
        theoreticalHours.setExit(exit);
        assertEquals(exit, theoreticalHours.getExit());
    }
}
