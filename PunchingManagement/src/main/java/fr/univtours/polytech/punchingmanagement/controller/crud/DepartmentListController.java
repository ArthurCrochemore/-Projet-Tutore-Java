package fr.univtours.polytech.punchingmanagement.controller.crud;

import fr.univtours.polytech.punchingmanagement.MainApp;
import fr.univtours.polytech.punchingmanagement.model.Department;
import javafx.collections.ObservableList;

public class DepartmentListController extends AbstractListController<Department> {
    @Override
    public ObservableList<Department> getList() {
        return MainApp.getCompany().getDepartments();
    }

    @Override
    public CRUDResources<Department> getCRUDResources() {
        return CRUDResources.getDepartmentResources();
    }
}
