package fr.univtours.polytech.punchingmanagement;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import fr.univtours.polytech.punchingmanagement.model.PunchingDay;

public class TestPunchingDay {

    private PunchingDay punchingDay;
    private LocalDate date;
    private LocalTime entry;
    private LocalTime exit;

    @Before
    public void setUp() {
        punchingDay = new PunchingDay();
        date = LocalDate.of(2021, 1, 1);
        entry = LocalTime.of(9, 0);
        exit = LocalTime.of(18, 0);
    }

    @Test
    public void testGetDate() {
        punchingDay.setDate(date);
        assertEquals(date, punchingDay.getDate());
    }

    @Test
    public void testSetDate() {
        punchingDay.setDate(date);
        assertEquals(date, punchingDay.getDate());
    }

    @Test
    public void testGetEntry() {
        punchingDay.setEntry(entry);
        assertEquals(entry, punchingDay.getEntry());
    }

    @Test
    public void testSetEntry() {
        punchingDay.setEntry(entry);
        assertEquals(entry, punchingDay.getEntry());
    }

    @Test
    public void testGetExit() {
        punchingDay.setExit(exit);
        assertEquals(exit, punchingDay.getExit());
    }

    @Test
    public void testSetExit() {
        punchingDay.setExit(exit);
        assertEquals(exit, punchingDay.getExit());
    }

    @Test
    public void testHasPunchedTwice_BothNull() {
        punchingDay.setEntry(null);
        punchingDay.setExit(null);
        assertEquals(false, punchingDay.hasPunchedTwice());
    }

    

    @Test
    public void testHasPunchedTwice_ExitNull() {
        punchingDay.setEntry(entry);
        punchingDay.setExit(null);
        assertEquals(false, punchingDay.hasPunchedTwice());
    }

    @Test
    public void testHasPunchedTwice_BothNotNull() {
        punchingDay.setEntry(entry);
        punchingDay.setExit(exit);
        assertEquals(true, punchingDay.hasPunchedTwice());
    }
}