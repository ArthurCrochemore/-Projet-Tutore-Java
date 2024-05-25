package fr.univtours.polytech.punchingmanagement.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;
import fr.univtours.polytech.punchingmanagement.MainApp;
import fr.univtours.polytech.punchingmanagement.model.Company;
import fr.univtours.polytech.punchingmanagement.model.Department;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import fr.univtours.polytech.punchingmanagement.model.PunchingDay;
import fr.univtours.polytech.punchingmanagement.model.TheoreticalHours;
import fr.univtours.polytech.punchingmanagement.model.WeeklySchedule;
import javafx.scene.control.Alert;

public class FakeDataGenerator {
    private Company company;
    private RandomGenerator random;


    public FakeDataGenerator() {
        company = MainApp.getCompany();
        random = RandomGeneratorFactory.getDefault().create();
    }

	public void askToFillWithFakeData() {
        // Open a dialog to ask if the user wants to fill the company with fake data
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fill with fake data");
        alert.setHeaderText("The company is empty, do you want to fill it with fake data ?");
        alert.setContentText("This will create fake departments and employees");
        alert.showAndWait();
        if (alert.getResult().getButtonData().isDefaultButton()) {
            System.out.println("Company is empty, filling with fake data");
            fillWithFakeData();

            alert.setTitle("Fill with random fake data");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to fill the company with random fake data too ?");
            alert.showAndWait();
            if (alert.getResult().getButtonData().isDefaultButton()) {
                System.out.println("Filling with random fake data");
                fillWithRandomFakeData();
            }
        }
	}

    public void fillWithFakeData() {
        Department dInfo = new Department("info");
        Department dAdmin = new Department("administration");
        Department dProf = new Department("prof");
        company.addDepartment(dInfo);
        company.addDepartment(dAdmin);
        company.addDepartment(dProf);
        Employee eJeanDupont = new Employee(UUID.fromString("f9aa8da9-7b4b-4c75-9a9b-850e1dddd254"), "Jean", "Dupont", LocalDate.of(2023, 6, 1));
        Employee ePierreMartin = new Employee(UUID.fromString("fc8d0eb7-e899-4139-8c87-93edae47a75d"), "Pierre", "Martin", LocalDate.of(2023, 6, 10));
        company.addEmployee(eJeanDupont);
        company.addEmployee(ePierreMartin);
        eJeanDupont.setDepartment(dInfo);
        ePierreMartin.setDepartment(dAdmin);

        eJeanDupont.setEmploymentDate(LocalDate.of(2022, 12, 14));
        ePierreMartin.setEmploymentDate(LocalDate.of(2022, 11, 12));
        
        TheoreticalHours thHuitSeize = new TheoreticalHours(LocalTime.of(8, 0), LocalTime.of(16, 0));
        TheoreticalHours thNeufDixsept = new TheoreticalHours(LocalTime.of(9, 0), LocalTime.of(17, 0));
        TheoreticalHours thDixDixHuit = new TheoreticalHours(LocalTime.of(10, 0), LocalTime.of(18, 0));
        TheoreticalHours thnull = new TheoreticalHours(null, null);
        
        WeeklySchedule wsJeanDupont = eJeanDupont.getWeeklySchedule();
        wsJeanDupont.addTheoreticalHours(DayOfWeek.MONDAY, thHuitSeize);
        wsJeanDupont.addTheoreticalHours(DayOfWeek.TUESDAY, thNeufDixsept);
        wsJeanDupont.addTheoreticalHours(DayOfWeek.WEDNESDAY, thHuitSeize);
        wsJeanDupont.addTheoreticalHours(DayOfWeek.THURSDAY, thHuitSeize);
        wsJeanDupont.addTheoreticalHours(DayOfWeek.FRIDAY, thDixDixHuit);
        wsJeanDupont.addTheoreticalHours(DayOfWeek.SATURDAY, thHuitSeize);
        wsJeanDupont.addTheoreticalHours(DayOfWeek.SUNDAY, thnull);
        
        WeeklySchedule wsPierreMartin = ePierreMartin.getWeeklySchedule();
        wsPierreMartin.addTheoreticalHours(DayOfWeek.MONDAY, thNeufDixsept);
        wsPierreMartin.addTheoreticalHours(DayOfWeek.TUESDAY, thHuitSeize);
        wsPierreMartin.addTheoreticalHours(DayOfWeek.WEDNESDAY, thDixDixHuit);
        wsPierreMartin.addTheoreticalHours(DayOfWeek.THURSDAY, thNeufDixsept);
        wsPierreMartin.addTheoreticalHours(DayOfWeek.FRIDAY, thHuitSeize);
        wsPierreMartin.addTheoreticalHours(DayOfWeek.SATURDAY, thNeufDixsept);
        wsPierreMartin.addTheoreticalHours(DayOfWeek.SUNDAY, thnull);
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 2), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 3), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 4), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 5), LocalTime.of(8, 30), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 6), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 7), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 9), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 10), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 11), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 12), LocalTime.of(8, 30), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 13), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 14), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 16), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 17), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 18), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 19), LocalTime.of(8, 30), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 20), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 21), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 23), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 24), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 25), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 26), LocalTime.of(8, 30), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 27), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 28), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 30), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 1, 31), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 1), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 2), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 3), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 4), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 6), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 7), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 8), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 9), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 10), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 11), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 13), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 14), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 15), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 16), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 17), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 18), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 20), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 21), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 22), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 23), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 24), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 25), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 27), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 2, 28), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 1), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 2), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 3), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 4), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 6), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 7), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 8), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 9), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 10), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 11), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 13), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 14), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 15), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 16), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 17), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 18), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 20), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 21), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 22), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 23), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 24), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 25), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 27), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 28), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 29), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 30), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 3, 31), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 1), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 3), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 4), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 5), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 6), LocalTime.of(8, 30), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 7), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 8), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 10), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 11), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 12), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 13), LocalTime.of(8, 30), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 14), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 15), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 17), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 18), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 19), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 20), LocalTime.of(8, 30), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 21), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 22), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 24), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 25), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 26), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 27), LocalTime.of(8, 30), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 28), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 4, 29), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 1), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 2), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 3), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 4), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 5), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 6), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 8), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 9), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 10), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 11), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 12), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 13), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 15), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 16), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 17), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 18), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 19), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 20), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 22), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 23), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 24), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 25), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 26), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 27), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 29), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 30), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 5, 31), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 1), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 2), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 3), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 5), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 6), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 7), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 8), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 9), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 10), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 11), LocalTime.of(8, 30), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 12), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 13), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 14), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 15), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 16), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 17), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 19), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 20), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 21), LocalTime.of(8, 15), LocalTime.of(14, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 22), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 23), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 24), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 26), LocalTime.of(8, 00), LocalTime.of(13, 15)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 27), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 28), LocalTime.of(8, 15), LocalTime.of(14, 0)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 29), LocalTime.of(7, 45), LocalTime.of(14, 45)));
        eJeanDupont.addPunching(new PunchingDay(eJeanDupont, LocalDate.of(2023, 6, 30), LocalTime.of(8, 00), LocalTime.of(14, 00)));
        
        
        
        ePierreMartin.addPunching(new PunchingDay(ePierreMartin, LocalDate.of(2023, 3, 1), LocalTime.of(8, 00), LocalTime.of(14, 15)));
        ePierreMartin.addPunching(new PunchingDay(ePierreMartin, LocalDate.of(2023, 3, 2), LocalTime.of(8, 30), LocalTime.of(14, 30)));
        ePierreMartin.addPunching(new PunchingDay(ePierreMartin, LocalDate.of(2023, 3, 3), LocalTime.of(7, 45), LocalTime.of(14, 00)));
        ePierreMartin.addPunching(new PunchingDay(ePierreMartin, LocalDate.of(2023, 3, 4), LocalTime.of(8, 15), LocalTime.of(15, 45)));
        
        ePierreMartin.addPunching(new PunchingDay(ePierreMartin, LocalDate.of(2023, 3, 6), LocalTime.of(8, 15), LocalTime.of(14, 15)));
    }

    private void fillWithRandomFakeData() {
        for (int i = 0; i < 3; i++) {
            company.addEmployee(createRandomEmployee());
        }
    }

    /**
     * Create a random employee with random punchings around 8h15 - 18h30
     */
    private Employee createRandomEmployee() {
        String firstName = "John" + random.nextInt(10000);
        String lastName = "Doe";

        // Create a random employee
        Employee employee = new Employee(firstName, lastName);
        employee.setEmploymentDate(getRandomDateSinceNow(1.5));

        // Get a random department
        Department department = getRandomDepartment();
        employee.setDepartment(department);

        // Create a random weekly schedule
        WeeklySchedule weeklySchedule = employee.getWeeklySchedule();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            weeklySchedule.addTheoreticalHours(dayOfWeek, createRandomTheoreticalHours());
        }

        // Create random punchings (6 months in the company)
        for(int day = 0; day < 180; day++) {
            LocalDate date = employee.getEmploymentDate().plusDays(day);
            if (date.isAfter(LocalDate.now())) {
                break;
            }
            addRandomPunching(employee, date);
        }

        return employee;
    }

    private Department getRandomDepartment() {
        return company.getDepartments().get(random.nextInt(company.getDepartments().size()));
    }

    private LocalDate getRandomDateSinceNow(double maxYearsSinceNow) {
        int relativeDays = random.nextInt((int) (365 * maxYearsSinceNow));
        return LocalDate.of(2023, 6, 15).minusDays(relativeDays);
    }

    private static final LocalTime[] POSSIBLE_ENTRY_TIMES = {
            LocalTime.of(8, 15),
            LocalTime.of(10, 30),
            LocalTime.of(14, 00),
            LocalTime.of(16, 15),
    };
    private static final LocalTime[] POSSIBLE_EXIT_TIMES = {
            LocalTime.of(9, 15),
            LocalTime.of(11, 30),
            LocalTime.of(15, 00),
            LocalTime.of(17, 15),
    };
    private TheoreticalHours createRandomTheoreticalHours() {
        if (random.nextDouble() > 0.6) {
            return null;
        } else {
            // Can start at : 08h15, 10h30, 14h00, 16h15
            // Can end at : 10h15, 12h30, 16h00, 18h15
            int indexEntry = random.nextInt(POSSIBLE_ENTRY_TIMES.length);
            LocalTime entry = POSSIBLE_ENTRY_TIMES[indexEntry];

            int indexExit = random.nextInt(indexEntry, POSSIBLE_EXIT_TIMES.length);
            LocalTime exit = POSSIBLE_EXIT_TIMES[indexExit];

            return new TheoreticalHours(entry, exit);
        }
    }

    private void addRandomPunching(Employee employee, LocalDate date) {
        // Get the theoretical hours
        TheoreticalHours th = employee.getWeeklySchedule().getTheoreticalHours(date);
        if (th == null) {
            // No theoretical hours for this day
            return;
        }
        if(random.nextDouble() > 0.8) {
            // 20% chance to not be present
            return;
        }

        // Generate postive and negative random values, near 0
        double relativeEntryTime = random.nextDouble() * random.nextDouble();
        double relativeExitTime = random.nextDouble() * random.nextDouble();

        // Compute the entry and exit time
        LocalTime entryTime = th.getEntry().plusMinutes((long) (relativeEntryTime * 4 * 60));
        LocalTime exitTime = th.getExit().plusMinutes((long) (relativeExitTime * 4 * 60));

        if (exitTime.isBefore(entryTime)) {
            // Swap the times
            LocalTime tmp = entryTime;
            entryTime = exitTime;
            exitTime = tmp;
        }

        // Round the times to the nearest 15 minutes
        entryTime = TimeUtils.roundTo15Minutes(entryTime);
        exitTime = TimeUtils.roundTo15Minutes(exitTime);

        // Add the punching
        employee.addPunching(new PunchingDay(employee, date, entryTime, exitTime));
    }
}
