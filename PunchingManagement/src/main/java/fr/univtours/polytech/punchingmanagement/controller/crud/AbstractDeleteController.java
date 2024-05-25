package fr.univtours.polytech.punchingmanagement.controller.crud;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public abstract class AbstractDeleteController<T> extends AbstractCRUDController<T> {
    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    private T item;

    public T getItem() {
        return item;
    }

    public void initializeData(T item) {
        this.item = item;
    }

    public void initialize() {
        super.initialize();
        deleteButton.setOnKeyPressed(this::handleKeyPress);
        deleteButton.setOnAction(event -> deleteItemAndClose());
        cancelButton.setOnKeyPressed(this::handleKeyPress);
        cancelButton.setOnAction(event -> close());
    }

    @Override
    protected void refresh() {
        // Update with override method
        initializeData(getItem());
    }

    /**
     * Delete the item
     */
    protected abstract void deleteItem();

    /**
     * Delete the item and close the window
     */
    private void deleteItemAndClose() {
        deleteItem();
        setHasChanged();
        close();
    }
}
