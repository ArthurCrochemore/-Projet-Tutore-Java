package fr.univtours.polytech.punchingmanagement.controller.crud;

import java.util.function.Consumer;

import fr.univtours.polytech.punchingmanagement.controller.crud.CRUDResources.CRUDAction;
import javafx.fxml.FXML;
import javafx.scene.AccessibleRole;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Abstract class for CRUD controllers.
 * This class is extended by all CRUD controllers.
 */
public abstract class AbstractCRUDController<T> {

    /**
     * Initialize the controller with the given item
     * 
     * @param item the item
     */
    public abstract void initializeData(T item);

    /**
     * Get the item of the controller
     * 
     * @return the item
     */
    public abstract T getItem();

    /**
     * Get the resources of the item
     * 
     * @return the resources for the item
     */
    public abstract CRUDResources<T> getCRUDResources();

    /**
     * Refresh the data of the controller
     */
    protected abstract void refresh();

    @FXML
    protected Pane root;

    private Consumer<Boolean> closeCallback;

    private boolean hasChanged = false;

    /**
     * Initialize the controller
     */
    public void initialize() {
        root.setAccessibleRole(AccessibleRole.DIALOG);
    }

    /**
     * Handle a key press, if the key is ESCAPE, close the dialog
     * 
     * @param event the key event
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            close();
        }
    }

    /**
     * Close the dialog and call the close callback
     */
    public void close() {
        Stage sb = (Stage) root.getScene().getWindow();
        sb.close();
        onClosing();
    }

    /**
     * Call the close callback when the dialog is closing
     */
    private void onClosing() {
        if (closeCallback != null) {
            closeCallback.accept(hasChanged);
        }
    }

    /**
     * Set the close callback
     * 
     * @param closeCallback the close callback, the parameter is true if something has changed
     */
    public void setCloseCallback(Consumer<Boolean> closeCallback) {
        this.closeCallback = closeCallback;
        getWindow().setOnCloseRequest(event -> onClosing());
    }

    /**
     * Set hasChanged to true for the close callback
     */
    protected void setHasChanged() {
        this.hasChanged = true;
    }

    /**
     * Called when a child dialog is closed
     * 
     * @param hasChanged true if something has changed
     */
    protected void onChildDialogClosed(boolean hasChanged) {
        if (hasChanged) {
            setHasChanged();
            refresh();
        }
    }

    /**
     * Get the window of the dialog
     * 
     * @return the window of the dialog
     */
    protected Window getWindow() {
        return root.getScene().getWindow();
    }

    /**
     * Open a CRUD dialog, with a close callback to refresh this dialog
     * 
     * @param resources the resources for the item
     * @param action    the action to do
     * @param item      the item to use (can be null)
     */
    public <S> void openCRUDDialog(CRUDResources<S> resources, CRUDAction action, S item) {
        resources.open(action, getWindow(), item, this::onChildDialogClosed);
    }

    /**
     * Open a CRUD dialog with the item of this controller
     * 
     * @param action the action to do
     */
    public void openCRUDDialog(CRUDAction action) {
        openCRUDDialog(getCRUDResources(), action, getItem());
    }

    /**
     * Open a CREATE dialog for this type of item
     */
    @FXML
    public void openCreateDialog() {
        openCRUDDialog(CRUDAction.CREATE);
    }
    
    /**
     * Open a READ dialog for the selected item
     */
    @FXML
    public void openReadDialog() {
        openCRUDDialog(CRUDAction.READ);
    }

    /**
     * Open an UPDATE dialog for the selected item
     */
    @FXML
    public void openUpdateDialog() {
        openCRUDDialog(CRUDAction.UPDATE);
    }

    /**
     * Open a DELETE dialog for the selected item
     */
    @FXML
    public void openDeleteDialog() {
        openCRUDDialog(CRUDAction.DELETE);
    }

    /**
     * Copy the given text to the clipboard
     * 
     * @param text the text to copy
     */
    public void copyToClipboard(String text) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }
}
