package fr.univtours.polytech.punchingmanagement;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Test;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;

public class TestUtils {

	private LocalTime time1 = LocalTime.of(23, 52);
	private LocalTime time2 = LocalTime.of(02, 47);
	private LocalTime time3 = LocalTime.of(0, 0);

	@Test
	public void testAroundTime() {
		assertEquals(LocalTime.of(20, 30), TimeUtils.roundTo15Minutes(LocalTime.of(20, 23))); // cas normal
		assertEquals(LocalTime.of(23, 45), TimeUtils.roundTo15Minutes(LocalTime.of(23, 55))); // cas tard
		assertEquals(LocalTime.of(21, 00), TimeUtils.roundTo15Minutes(LocalTime.of(20, 58))); // cas heure supérieure
		assertEquals(LocalTime.of(10, 30), TimeUtils.roundTo15Minutes(LocalTime.of(10, 30))); // cas heure déjà arrondie
	}

	@Test
	public void convertLocalTimeToMinute() {
		assertEquals(1432, TimeUtils.convertLocalTimeToMinute(time1));
		assertEquals(167, TimeUtils.convertLocalTimeToMinute(time2));
		assertEquals(0, TimeUtils.convertLocalTimeToMinute(time3));
	}

	@Test
	public void testWorkingTime() {
		assertEquals(1265, TimeUtils.workingTime(time2, time1));
		assertEquals(-1432, TimeUtils.workingTime(time1, time3));
		assertEquals(0, TimeUtils.workingTime(time1, time1));
		assertEquals(0, TimeUtils.workingTime(time1, time1));
	}
}
