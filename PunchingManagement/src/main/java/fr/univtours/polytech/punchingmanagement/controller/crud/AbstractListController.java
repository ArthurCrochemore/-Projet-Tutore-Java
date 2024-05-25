package fr.univtours.polytech.punchingmanagement.controller.crud;

import fr.univtours.polytech.punchingmanagement.controller.crud.CRUDResources.CRUDAction;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class AbstractListController<T> extends AbstractCRUDController<T> {

    public abstract ObservableList<T> getList();

    @FXML
    protected TableView<T> view;

    @FXML
    public Button createButton;

    @FXML
    public Button readButton;

    @FXML
    public Button editButton;

    @FXML
    public Button deleteButton;

    public T getItem() {
        if (view.getSelectionModel().getSelectedItem() == null)
            view.getSelectionModel().selectFirst();
        return view.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initializeData(T item) {
        throw new UnsupportedOperationException(
                "Method 'initializeData' should not be called on AbstractListController");
    }

    public void initialize() {
        super.initialize();
        view.setItems(getList());
        view.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
        view.addEventHandler(MouseEvent.MOUSE_CLICKED, this::mouseClick);
        createButton.setOnKeyPressed(this::handleKeyPress);
        readButton.setOnKeyPressed(this::handleKeyPress);
        editButton.setOnKeyPressed(this::handleKeyPress);
        deleteButton.setOnKeyPressed(this::handleKeyPress);
        createButton.setOnAction(event -> openCRUDDialog(CRUDAction.CREATE));
        readButton.setOnAction(event -> openCRUDDialog(CRUDAction.READ));
        editButton.setOnAction(event -> openCRUDDialog(CRUDAction.UPDATE));
        deleteButton.setOnAction(event -> openCRUDDialog(CRUDAction.DELETE));
    }

    @Override
    protected void refresh() {
        view.refresh();
    }
    
    /**
     * Handle key press event,
     * open Read dialog on ENTER or SPACE,
     * open Delete dialog on DELETE
     * 
     * @param event key evet
     */
    @Override
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case DELETE:
                openDeleteDialog(); // Delete selected item
                break;
            case ENTER:
            case SPACE:
                openReadDialog(); // Read selected item
                break;
            default:
                super.handleKeyPress(event);
        }
    }

    /**
     * Handle mouse click event,
     * open Read dialog on double click
     * 
     * @param event mouse event
     */
    public void mouseClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            openReadDialog(); // Read selected item
        }
    }
}
