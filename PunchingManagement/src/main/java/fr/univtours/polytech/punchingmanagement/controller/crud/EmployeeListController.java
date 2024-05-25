package fr.univtours.polytech.punchingmanagement.controller.crud;

import fr.univtours.polytech.punchingmanagement.MainApp;
import fr.univtours.polytech.punchingmanagement.model.Department;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class EmployeeListController extends AbstractListController<Employee> {
    
    @FXML
    TableColumn<Employee, String> columnDepartment;

    @Override
    public ObservableList<Employee> getList() {
        return MainApp.getCompany().getEmployees();
    }

    @Override
    public CRUDResources<Employee> getCRUDResources() {
        return CRUDResources.getEmployeeResources();
    }
    
    @Override
    public void initialize() {
        super.initialize();
        setColumnDepartmentFactory();
    }

    /**
     * Set the factory for the column department
     */
    private void setColumnDepartmentFactory() {
        columnDepartment.setCellValueFactory(cellData -> {
            Employee employee = cellData.getValue();
            Department department = employee.getDepartment();
            String departmentName = department == null ? "" : department.getName();
            return new ReadOnlyStringWrapper(departmentName);
        });
    }
}
