package fr.univtours.polytech.punchingmanagement;


import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.univtours.polytech.punchingmanagement.model.Company;
import fr.univtours.polytech.punchingmanagement.model.Department;
import fr.univtours.polytech.punchingmanagement.model.Employee;

public class TestCompany {

    private Company company;

    @Before 
    public void setUp() {
        company = new Company();
        MainApp.setCompany(company);
    }

    @Test
    public void testGetDepartments() {
        List<Department> departments = company.getDepartments();
        assertEquals(0, departments.size());
    }

    @Test
    public void testAddDepartment() {
        Department department = new Department("IT", UUID.randomUUID());
        company.addDepartment(department);

        List<Department> departments = company.getDepartments();
        assertEquals(1, departments.size());
        assertEquals(department, departments.get(0));
    }

    @Test
    public void testRemoveDepartment() {
        Department department = new Department("HR",UUID.randomUUID());
        company.addDepartment(department);

        company.removeDepartment(department);

        List<Department> departments = company.getDepartments();
        assertEquals(0, departments.size());
    }

    @Test
    public void testGetEmployees() {
        List<Employee> employees = company.getEmployees();
        assertEquals(0, employees.size());
    }
    

   
}

