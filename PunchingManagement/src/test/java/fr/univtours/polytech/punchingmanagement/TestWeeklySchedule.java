package fr.univtours.polytech.punchingmanagement;


import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.univtours.polytech.punchingmanagement.model.TheoreticalHours;
import fr.univtours.polytech.punchingmanagement.model.WeeklySchedule;

public class TestWeeklySchedule {

    private WeeklySchedule weeklySchedule;

    @Before
    public void setUp() {
        weeklySchedule = new WeeklySchedule(null);
    }

    @Test
    public void testGetUuid() {
        UUID uuid = UUID.randomUUID();
        weeklySchedule.setUUID(uuid);
        assertEquals(uuid, weeklySchedule.getUUID());
    }

    @Test
    public void TheoreticalHours() {
        LocalTime entry = LocalTime.of(9, 0);
        LocalTime exit = LocalTime.of(17, 0);

        TheoreticalHours theoreticalHours = new TheoreticalHours(entry, exit);

        assertEquals(entry, theoreticalHours.getEntry());
        assertEquals(exit, theoreticalHours.getExit());
    }
    
    @Test
    public void testSetDaysOfWeek() {
        
    	LocalTime entry = LocalTime.of(9, 0);
        LocalTime exit = LocalTime.of(17, 0);
        TheoreticalHours theoreticalHours = new TheoreticalHours(entry, exit);
        weeklySchedule.addTheoreticalHours(DayOfWeek.MONDAY, theoreticalHours);

        assertEquals(theoreticalHours, weeklySchedule.getTheoreticalHours(DayOfWeek.MONDAY));
    }
}


