package fr.univtours.polytech.punchingmanagement.controller;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import fr.univtours.polytech.punchingmanagement.MainApp;
import fr.univtours.polytech.punchingmanagement.model.Company;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import fr.univtours.polytech.punchingmanagement.model.PunchingDay;
import fr.univtours.polytech.punchingmanagement.model.TheoreticalHours;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ConsultDayController {
	private static final String LABEL_VALIDATE = "Validate";
	private static final String LABEL_HOUR_RATE = "Hour rate :";
	private static final String LABEL_NUM_WEEK = "Week :";
	private static final String LABEL_SCHEDULE = "Schedule :";
	private static final String LABEL_REAL = "Real :";
	private static final List<String> LIST_DAYS_OF_WEEK = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");

    @FXML
    private Pane root;

    int hourRate;
    int numWeek;
    List<Rectangle> scheduleQuarters;
    
    Company company;
    GridPane grid;
    GridPane gridLabel;
    
    public void initialize() {
    	grid = null;
    	gridLabel = null;
    	hourRate = 0;
    	company = MainApp.getCompany();
    	
    	GridPane ouai = new GridPane();
    	
    	ComboBox<Employee> selectEmployee = new ComboBox<>(company.getEmployees());
    	ouai.add(selectEmployee, 1, 0);
    	
    	DatePicker calendar = new DatePicker();
    	ouai.add(calendar, 0, 0);

    	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
    	    public void handle(ActionEvent e) {    	    	
    	    	hourRate = 0;
    	    	numWeek = 0;
    	    	
    	    	LocalDate date = calendar.getValue();
    	    	Employee employee = (Employee) selectEmployee.getValue();
    	    	
    	    	if (employee != null && date != null) {
    	    		if (grid != null) {
        	    		root.getChildren().remove(grid);
        	    		root.getChildren().remove(gridLabel);
        	    	}
        	    	
        	    	initializeData(date, employee);
    	    	}
    	    }
    	};
    	
    	Button validate = new Button();
    	validate.setText(LABEL_VALIDATE);
    	validate.setOnAction(event);

    	ouai.add(validate, 2, 0);
    	ouai.setHgap(60);
		ouai.setId("gridInput");
    	root.getChildren().add(ouai);
    }

    public void initializeData(LocalDate day, Employee employee) {
    	LocalDate monday = day.minusDays((getIndexDay(day) - 1)%7);
    	numWeek = monday.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());  

        int dailyStart;
        int dailyEnd;
        int dailyRealStart;
        int dailyRealEnd;
        
        PunchingDay dayAnalyse;

		grid = new GridPane();
		for (int i = 0; i < 13; i++) {
			int h = i + 7;
			Label label = new Label(Integer.toString(h) + "h");
			GridPane.setValignment(label, VPos.TOP);
			grid.add(label, 0, 4 * i + 1, 1, 4);
		}
		
		for (int i = 0; i < 7; i++) {
			Label today =  new Label(LIST_DAYS_OF_WEEK.get(i));
			if (monday.plusDays(i).getDayOfYear() == day.getDayOfYear()) {
				today.setTextFill(Color.RED);
			}
			GridPane.setHalignment(today, HPos.CENTER);
			grid.add(today, i * 2 + 1, 0, 2, 1);
		}

		scheduleQuarters = new ArrayList<>();
		
		for (int i = 0; i < 7; i ++) {
			LocalDate date = monday.plusDays(i);

			if (date.isBefore(LocalDate.now()) && date.isAfter(employee.getEmploymentDate())) {
				dayAnalyse = employee.getPunching(date);
				TheoreticalHours th = employee.getWeeklySchedule().getTheoreticalHours(DayOfWeek.of(i + 1));
				if (th != null && th.isWorking()) {
					dailyStart = getValueOfHour(th.getEntry());
					dailyEnd = getValueOfHour(th.getExit());
					this.hourRate += - (dailyEnd - dailyStart);
				}
				else {
					dailyStart = 49;
		        	dailyEnd = 0;
				}
			}
			else {
				dayAnalyse = null;
				dailyStart = 49;
	        	dailyEnd = 0;
			}
			
			if (dayAnalyse == null) {
				dailyRealStart = 49;
	        	dailyRealEnd = 0;
			}
			else 
			{
				dailyRealStart = getValueOfHour(dayAnalyse.getEntry());
		        dailyRealEnd = getValueOfHour(dayAnalyse.getExit());
		        
		        this.hourRate += dailyRealEnd - dailyRealStart;
			}
			
			for (int j = 0; j < 49; j ++) {
				// Add a rectangle for each day
				// Add nothing if the day is not in the year

				Rectangle scheduleQuarter = new Rectangle(30, 10);
				scheduleQuarter.getStyleClass().add("day");
				grid.add(scheduleQuarter, i*2 + 1, j + 1);
				scheduleQuarters.add(scheduleQuarter);
				if (j < dailyStart || j > dailyEnd)
					scheduleQuarter.setFill(Color.GRAY);
				else
					scheduleQuarter.setFill(Color.GREEN);

				scheduleQuarter = new Rectangle(30, 10);
				scheduleQuarter.getStyleClass().add("day");
				grid.add(scheduleQuarter, i*2 + 2, j + 1);
				scheduleQuarters.add(scheduleQuarter);
				if (j < dailyRealStart || j > dailyRealEnd)
					scheduleQuarter.setFill(Color.GRAY);
				else
					scheduleQuarter.setFill(Color.RED);
			}
		}
		
        grid.setId("gridGraph");
        grid.setHgap(5);
        grid.setVgap(-1);
        root.getChildren().add(grid);
		
   	 	gridLabel = new GridPane();
        gridLabel.add(new Label(LABEL_HOUR_RATE), 0, 0);
        gridLabel.add(new Label(hourRate/4 + "h"), 1, 0);
        gridLabel.add(new Label(LABEL_SCHEDULE), 4, 0);
        gridLabel.add(new Label(LABEL_REAL), 7, 0);
        gridLabel.add(new Label(LABEL_NUM_WEEK), 10, 0);
        gridLabel.add(new Label(numWeek + ""), 11, 0);
        
        Rectangle legend = new Rectangle(30, 10);
        legend.setFill(Color.GREEN);
        gridLabel.add(legend, 5, 0);
        
        Rectangle legendReal = new Rectangle(30, 10);
        legendReal.setFill(Color.RED);
        gridLabel.add(legendReal, 8, 0);
        
        gridLabel.setId("gridLabelLegend");
        gridLabel.setHgap(10);
        gridLabel.setVgap(5);	
        root.getChildren().add(gridLabel);
    }
    
    public int getValueOfHour(LocalTime hour) {
    	return (hour.getHour() - 7) * 4 + (hour.getMinute() / 15);
    }
    
    public int getIndexDay(LocalDate day) {
    	DayOfWeek dayOfWeek = day.getDayOfWeek();
    	return dayOfWeek.getValue();
    }
   
}
