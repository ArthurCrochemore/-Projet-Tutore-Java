package fr.univtours.polytech.punchingmanagement.controller.crud;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public abstract class AbstractCreateUpdateController<T> extends AbstractCRUDController<T> {

    private T item;

    public T getItem() {
        return item;
    }

    public void initializeData(T item) {
        this.item = item;
        okButton.setText("Update");
    }

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    public void initialize() {
        super.initialize();
        okButton.setOnKeyPressed(this::handleKeyPress);
        okButton.setOnAction(event -> createOrUpdateItem());
        cancelButton.setOnKeyPressed(this::handleKeyPress);
        cancelButton.setOnAction(event -> close());
    }

    @Override
    protected void refresh() {
        // Update with override method
        if (item != null)
            initializeData(getItem());
    }

    /**
     * Create an item with the given data
     */
    protected abstract void createItem();

    /**
     * Update the current item with the given data
     */
    protected abstract void updateItem(T item);

    /**
     * Create Or Update the item with the given data, then close the window
     */
    protected void createOrUpdateItem() {
        if (item == null) {
            createItem();
        } else {
            updateItem(item);
        }
        setHasChanged();
        close();
    }
}
