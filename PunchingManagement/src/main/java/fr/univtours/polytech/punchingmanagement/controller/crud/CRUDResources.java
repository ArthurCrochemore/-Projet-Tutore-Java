package fr.univtours.polytech.punchingmanagement.controller.crud;

import java.io.IOException;
import java.util.function.Consumer;

import fr.univtours.polytech.punchingmanagement.controller.MainController;
import fr.univtours.polytech.punchingmanagement.model.Department;
import fr.univtours.polytech.punchingmanagement.model.Employee;
import fr.univtours.polytech.punchingmanagement.model.PunchingDay;
import javafx.fxml.FXMLLoader;
import javafx.stage.Window;

/**
 * A class that contains all the resources for the CRUD controllers.
 */
public class CRUDResources<T> {
    private String listTitle;
    private String listResource;
    private String createTitle;
    private String createResource;
    private String readTitle;
    private String readResource;
    private String updateTitle;
    private String updateResource;
    private String deleteTitle;
    private String deleteResource;

    /**
     * The type of CRUD dialog.
     */
    public enum CRUDAction {
        LIST, CREATE, READ, UPDATE, DELETE
    }

    private CRUDResources() {
    }

    /**
     * Get the resources for the class Employee
     *
     * @return the resources for the class Employee
     */
    public static CRUDResources<Employee> getEmployeeResources() {
        CRUDResources<Employee> resources = new CRUDResources<>();
        resources.setList("Employee list", "view/CRUD/EmployeeList.fxml");
        resources.setCreate("Create employee", "view/CRUD/EmployeeCreateUpdate.fxml");
        resources.setRead("Read employee", "view/CRUD/EmployeeRead.fxml");
        resources.setUpdate("Update employee", "view/CRUD/EmployeeCreateUpdate.fxml");
        resources.setDelete("Delete employee", "view/CRUD/EmployeeDelete.fxml");
        return resources;
    }

    /**
     * Get the resources for the class Department
     * 
     * @return the resources for the class Department
     */
    public static CRUDResources<Department> getDepartmentResources() {
        CRUDResources<Department> resources = new CRUDResources<>();
        resources.setList("Department list", "view/CRUD/DepartmentList.fxml");
        resources.setCreate("Create department", "view/CRUD/DepartmentCreateUpdate.fxml");
        resources.setRead("Read department", "view/CRUD/DepartmentRead.fxml");
        resources.setUpdate("Update department", "view/CRUD/DepartmentCreateUpdate.fxml");
        resources.setDelete("Delete department", "view/CRUD/DepartmentDelete.fxml");
        return resources;
    }

    /**
     * Get the resources for the class PunchingDay
     * 
     * @return the resources for the class PunchingDay
     */
    public static CRUDResources<PunchingDay> getPunchingDayResources() {
        CRUDResources<PunchingDay> resources = new CRUDResources<>();
        resources.setList("Punching list", "view/CRUD/PunchingList.fxml");
        resources.setCreate("Create punching", "view/CRUD/PunchingCreateUpdate.fxml");
        resources.setRead("Read punching", "view/CRUD/PunchingRead.fxml");
        resources.setUpdate("Update punching", "view/CRUD/PunchingCreateUpdate.fxml");
        resources.setDelete("Delete punching", "view/CRUD/PunchingDelete.fxml");
        return resources;
    }

    /**
     * Set the LIST resource
     * 
     * @param title    the title of the dialog
     * @param resource the location of the FXML file
     */
    private void setList(String title, String resource) {
        this.listTitle = title;
        this.listResource = resource;
    }

    /**
     * Set the CREATE resource
     * 
     * @param title    the title of the dialog
     * @param resource the location of the FXML file
     */
    private void setCreate(String title, String resource) {
        this.createTitle = title;
        this.createResource = resource;
    }

    /**
     * Set the READ resource
     * 
     * @param title    the title of the dialog
     * @param resource the location of the FXML file
     */
    private void setRead(String title, String resource) {
        this.readTitle = title;
        this.readResource = resource;
    }

    /**
     * Set the UPDATE resource
     * 
     * @param title    the title of the dialog
     * @param resource the location of the FXML file
     */
    private void setUpdate(String title, String resource) {
        this.updateTitle = title;
        this.updateResource = resource;
    }

    /**
     * Set the DELETE resource
     * 
     * @param title    the title of the dialog
     * @param resource the location of the FXML file
     */
    private void setDelete(String title, String resource) {
        this.deleteTitle = title;
        this.deleteResource = resource;
    }

    /**
     * Open a CRUD dialog
     * 
     * @param parent   the parent window
     * @param resource the location of the FXML file
     * @param title    the title of the dialog
     * @param callback the callback when the dialog is closed
     * @return the controller of the new dialog
     */
    public AbstractCRUDController<T> openWindow(Window parent, String resource, String title,
            Consumer<Boolean> callback) {
        try {
            FXMLLoader loader = MainController.loadFXML(resource);
            MainController.openWindowModal(loader, title, parent);
            AbstractCRUDController<T> controller = loader.getController();
            controller.setCloseCallback(callback);
            return controller;
        } catch (IOException e) {
            System.out.println("Error while loading " + resource + " : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Open a CRUD dialog with an item
     * 
     * @param action   the type of dialog
     * @param parent   the parent window
     * @param item     the item to display
     * @param callback the callback when the dialog is closed
     * @return the controller of the new dialog, null if the item is null
     */
    public AbstractCRUDController<T> openWithItem(Window parent, String resource, String title, T item,
            Consumer<Boolean> callback) {
        if (item == null) {
            return null;
        }
        AbstractCRUDController<T> controller = openWindow(parent, resource, title, callback);
        if (controller != null) {
            controller.initializeData(item);
            return controller;
        }
        return null;
    }

    /**
     * Open a CRUD dialog
     * 
     * @param action   the type of dialog
     * @param parent   the parent window
     * @param item     the item to display (can be null for CREATE and LIST)
     * @param callback the callback when the dialog is closed
     */
    public void open(CRUDAction action, Window parent, T item, Consumer<Boolean> callback) {
        String resource;
        String title;
        switch (action) {
            case LIST:
                resource = listResource;
                title = listTitle;
                break;
            case CREATE:
                resource = createResource;
                title = createTitle;
                break;
            case READ:
                resource = readResource;
                title = readTitle;
                break;
            case UPDATE:
                resource = updateResource;
                title = updateTitle;
                break;
            case DELETE:
                resource = deleteResource;
                title = deleteTitle;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }

        if (action == CRUDAction.CREATE || action == CRUDAction.LIST)
            openWindow(parent, resource, title, callback);
        else
            openWithItem(parent, resource, title, item, callback);
    }
}
