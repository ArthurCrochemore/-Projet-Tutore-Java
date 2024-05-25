package fr.univtours.polytech.punchingmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.univtours.polytech.punchingmanagement.model.Company;
import fr.univtours.polytech.punchingmanagement.model.Department;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import fr.univtours.polytech.punchingmanagement.model.PunchingDay;
import fr.univtours.polytech.punchingmanagement.model.TheoreticalHours;

public class TestEmployee {

    private UUID uuid;
    private Employee employee;

    @Before
    public void setUp() {
        MainApp.setCompany(new Company());
        LocalDate employmentDate = LocalDate.now();
        uuid = UUID.randomUUID();
        employee = new Employee(uuid, "?", "?", employmentDate);
    }

    @Test
    public void testGetEmploymentDate() {
        assertEquals(LocalDate.now(), employee.getEmploymentDate());
    }

    @Test
    public void testSetEmploymentDate() {
        LocalDate newEmploymentDate = LocalDate.of(2022, 1, 1);
        employee.setEmploymentDate(newEmploymentDate);
        assertEquals(newEmploymentDate, employee.getEmploymentDate());
    }

    @Test
    public void testGetWeeklySchedule() {
        assertNotNull(employee.getWeeklySchedule());
    }

    @Test
    public void testEmployeeConstructor() {
        assertEquals(uuid, employee.getUuid());
        assertEquals("?", employee.getFirstName());
        assertEquals("?", employee.getName());
    }

    @Test
    public void testGetDepartmentUUID() {
        Department department = new Department("Department");
        employee.setDepartment(department);
        assertEquals(department.getUuid(), employee.getDepartmentUUID());
    }

    @Test
    public void testEmployee() {
        UUID uuid = UUID.randomUUID();
        String firstName = "John";
        String name = "Doe";
        LocalDate employmentDate = LocalDate.of(2020, 1, 1);
        int hourlyRate = 17;
        Employee employee = new Employee(uuid, firstName, name, employmentDate);
        employee.setHourlyRate(hourlyRate);

        assertEquals(uuid, employee.getUuid());
        assertEquals(firstName, employee.getFirstName());
        assertEquals(name, employee.getName());
        assertEquals(employmentDate, employee.getEmploymentDate());
        assertEquals(hourlyRate, employee.getHourlyRate());
    }

    @Test
    public void testaddTimeToHourlyRate() {
        int minuteToAdd = -2;
        employee.setHourlyRate(126);
        employee.addTimeToHourlyRate(minuteToAdd);
        assertEquals(124, employee.getHourlyRate());
    }

    @Test
    public void testAddPunching() {
        employee.setHourlyRate(0);

        TheoreticalHours theoreticalHours = new TheoreticalHours(LocalTime.of(8, 0), LocalTime.of(16, 0));
        employee.getWeeklySchedule().addTheoreticalHours(DayOfWeek.MONDAY, theoreticalHours);

        PunchingDay punchingDay = new PunchingDay(employee, LocalDate.of(2023, 1, 2), LocalTime.of(8, 30), LocalTime.of(16, 15));
        employee.addPunching(punchingDay);

        assertEquals(-15, employee.getHourlyRate()); // -15 minutes of credit

    }
}
