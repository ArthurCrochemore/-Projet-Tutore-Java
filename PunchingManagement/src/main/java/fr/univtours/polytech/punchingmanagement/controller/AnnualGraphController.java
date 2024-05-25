package fr.univtours.polytech.punchingmanagement.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AnnualGraphController {

    @FXML
    private GridPane root;

    int year;
    List<Rectangle> days;
    List<Tooltip> tooltips;

    GridPane grid;

    private static final List<String> daysOfWeek = Arrays.asList("Mon", "", "Wed", "", "Fri", "", "");
    private static final List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec");

    public void initialize() {
        // Wait for the year
        
        grid = new GridPane();
        for (int i = 0; i < 7; i++) {
            grid.add(new Label(daysOfWeek.get(i)), 0, i + 1);
        }
        for (int i = 0; i < 12; i++) {
            grid.add(new Label(months.get(i)), (int) Math.floor(i * 4.33 + 2), 0, 4, 1);
        }
    }

    public void initializeData(int year) {
        // Add a grid starting at (0,1) with 7 rows for the days of the week
        // Add a grid starting at (1,1) with 52 rows and 7 columns for the days of the
        // year
        this.year = year;

        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
        LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);
        int firstDayOfWeek = firstDayOfYear.getDayOfWeek().getValue();
        int lastDayOfWeek = lastDayOfYear.getDayOfWeek().getValue();

        days = new ArrayList<>();
        tooltips = new ArrayList<>();

        for (int i = 0; i < 53; i++) {
            for (int j = 0; j < 7; j++) {
                // Add a rectangle for each day
                // Add nothing if the day is not in the year
                if (i == 0 && j < firstDayOfWeek - 1) {
                    continue;
                }
                if (i == 52 && j > lastDayOfWeek - 1) {
                    continue;
                }
                Rectangle day = new Rectangle(10, 10);
                day.getStyleClass().add("day");
                grid.add(day, i + 1, j + 1);
                days.add(day);
                day.setFill(Color.GRAY);

                LocalDate date = LocalDate.ofYearDay(year, i * 7 + j + 2 - firstDayOfWeek);
                Tooltip t = new Tooltip(TimeUtils.format(date) + "\nNo data");
                Tooltip.install(day, t);
                tooltips.add(t);
            }
        }

        root.add(grid, 0, 1);
        grid.setId("gridGraph");
        grid.setHgap(5);
        grid.setVgap(5);
    }

    private Rectangle getDay(LocalDate date) {
        if (date.getYear() != year)
            throw new IllegalArgumentException("The date is not in the year");
        int dayOfYear = date.getDayOfYear();
        if(dayOfYear > days.size())
            throw new IllegalArgumentException("The date is not in the year");
        return days.get(dayOfYear - 1);
    }

    public void setDayColor(LocalDate date, Color color) {
        Rectangle day = getDay(date);
        day.setFill(color);
    }

    public void setTooltip(LocalDate date, String message) {
        if (date.getYear() != year)
            throw new IllegalArgumentException("The date is not in the year");
        Tooltip t = tooltips.get(date.getDayOfYear() - 1);
        t.setText(TimeUtils.format(date) + "\n" + message);
    }

    public void setClickAction(LocalDate date, Consumer<LocalDate> action) {
        Rectangle day = getDay(date);
        day.setOnMouseClicked(e -> action.accept(date));
        day.setCursor(Cursor.HAND);
    }
}
