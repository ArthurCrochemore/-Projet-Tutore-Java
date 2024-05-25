package fr.univtours.polytech.punchingmanagement.controller;

import java.io.IOException;
import java.time.LocalDate;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;
import fr.univtours.polytech.punchingmanagement.MainApp;
import fr.univtours.polytech.punchingmanagement.controller.crud.CRUDResources;
import fr.univtours.polytech.punchingmanagement.controller.crud.CRUDResources.CRUDAction;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import fr.univtours.polytech.punchingmanagement.model.PunchingDay;
import fr.univtours.polytech.punchingmanagement.model.TheoreticalHours;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Window;

public class ConsultAllController {

    @FXML
    private Pane root;

    @FXML
    private GridPane legend;

    public void initialize() throws IOException {
        int currentYear = LocalDate.now().getYear();
        for (Employee employee : MainApp.getCompany().getEmployees()) {
            FilteredList<PunchingDay> punchingHistory = employee.getPunchingHistory();
            int firstYear = punchingHistory.stream().map(p -> p.getDate().getYear()).min(Integer::compareTo).orElse(currentYear);
            
            for (int year = currentYear; year >= firstYear; year--) {
                final int y = year;
                FilteredList<PunchingDay> punchingsOfTheYear = new FilteredList<>(punchingHistory, punching -> punching.getDate().getYear() == y);
                if(punchingsOfTheYear.isEmpty())
                    continue;

                // Add a label with the name of the employee and the year
                Label label = new Label(employee.getFirstName() + " " + employee.getName() + " " + year);
                root.getChildren().add(label);
                label.setPadding(new Insets(20, 0, 0, 0));


                FXMLLoader loadedGraph = MainController.loadFXML("view/AnnualGraph.fxml");
                root.getChildren().add(loadedGraph.getRoot());
                AnnualGraphController graphController = loadedGraph.<AnnualGraphController>getController();

                graphController.initializeData(year);
                punchingsOfTheYear.forEach(punching -> setPunchingDay(punching, graphController));
            }
        }

        // Center the legend
        for(Node node : legend.getChildren()) {
            GridPane.setHalignment(node, HPos.CENTER);
        }
    }

    private void setPunchingDay(PunchingDay punching, AnnualGraphController graphController) {
        LocalDate date = punching.getDate();
        int minutes = TimeUtils.workingTime(punching.getEntry(), punching.getExit());

        TheoreticalHours th = punching.getTheoreticalHours();
        int workingTime = th == null ? 0 : th.theoreticalWorkingTime();

        setDayValue(date, minutes, workingTime, graphController);
        graphController.setClickAction(date, (event) -> {
            Window window = root.getScene().getWindow();
            CRUDResources.getPunchingDayResources().open(CRUDAction.READ, window, punching, hasChanged -> {
                if (hasChanged)
                    setPunchingDay(punching, graphController); // Update the day
            });
        });
    }

    private void setDayValue(LocalDate date, int workingTime, int theoricalHour, AnnualGraphController graphController) {
        int deltaMinutes = Math.abs(workingTime - theoricalHour);
        double value = Math.ceil(deltaMinutes / 60.) / 4.;

        if (workingTime < theoricalHour)
            value = -value;

        // If the employee has more than 4 hours of difference, the color is at 255
        // Else, the color is at 255 * (1 - value) (gradient of 4 colors, 1 per hour)

        Color color;
        if (value < 0) {
            value = -value;
            if (value > 1)
                value = 1;
            int v = (int) (255 * (1 - value));
            color = Color.rgb(255, v, v);
        } else {
            if (value > 1)
                value = 1;
            int v = (int) (255 * (1 - value));
            color = Color.rgb(v, 255, v);
        }
        graphController.setDayColor(date, color);
        graphController.setTooltip(date, "Working time : " + workingTime + " minutes (" + theoricalHour + " expected)");
    }
}
