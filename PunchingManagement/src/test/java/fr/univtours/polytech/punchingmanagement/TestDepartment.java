package fr.univtours.polytech.punchingmanagement;


import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.univtours.polytech.punchingmanagement.model.Company;
import fr.univtours.polytech.punchingmanagement.model.Department;
import fr.univtours.polytech.punchingmanagement.model.Employee;

public class TestDepartment {

    private Department department;

    @Before 
    public void setUp() {
        MainApp.setCompany(new Company());
        department = new Department("IT");
    }

    @Test
    public void testGetName() {
        assertEquals("IT", department.getName());
    }

    @Test
    public void testSetName() {
        department.setName("HR");
        assertEquals("HR", department.getName());
    }

    @Test
    public void testGetUuid() {
        UUID uuid = department.getUuid();
        assertEquals(uuid, department.getUuid());
    }

    @Test
    public void testSetUuid() {
        UUID newUuid = UUID.randomUUID();
        department.setUuid(newUuid);
        assertEquals(newUuid, department.getUuid());
    }

    @Test
    public void testAddEmployee() {
        Employee employee = new Employee("John", "Doe");
        
        MainApp.getCompany().addEmployee(employee);
        assertEquals(1, MainApp.getCompany().getEmployees().size());
        
        department.addEmployee(employee);
        assertEquals(department.getUuid(), employee.getDepartmentUUID());
        assertEquals(1, department.getNumberOfEmployees());
        assertEquals(employee, department.getEmployees().get(0));
        
        department.removeEmployee(employee);
        assertEquals(0, department.getEmployees().size());
    }
}

