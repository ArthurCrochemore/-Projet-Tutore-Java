package fr.univtours.polytech.punchingmanagement.controller.crud;

import java.time.LocalDate;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;
import fr.univtours.polytech.punchingmanagement.MainApp;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import fr.univtours.polytech.punchingmanagement.model.PunchingDay;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class PunchingListController extends AbstractListController<PunchingDay> {
    ObservableList<PunchingDay> punchingList = FXCollections.observableArrayList();

    @FXML
    TableColumn<PunchingDay, LocalDate> columnDate;

    @FXML
    TableColumn<PunchingDay, String> columnEmployee;

    @Override
    public ObservableList<PunchingDay> getList() {
        punchingList.setAll(MainApp.getCompany().getPunchingDays());
        return punchingList;
    }

    @Override
    public CRUDResources<PunchingDay> getCRUDResources() {
        return CRUDResources.getPunchingDayResources();
    }

    @Override
    public void initialize() {
        super.initialize();
        setColumnDateFactory();
        setColumnEmployeeFactory();
    }

    /**
     * Set the factory for the column date,
     * by default the date is displayed as YYYY-MM-DD
     */
    private void setColumnDateFactory() {
        columnDate.setCellFactory(column -> new TableCell<PunchingDay, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(TimeUtils.format(item));
                }
            }
        });
    }

    /**
     * Set the factory for the column employee
     */
    private void setColumnEmployeeFactory() {
        columnEmployee.setCellValueFactory(cellData -> {
            PunchingDay punchingDay = cellData.getValue();
            Employee employee = punchingDay.getEmployee();
            return new ReadOnlyStringWrapper(employee.getFirstNameLastName());
        });
    }
}
