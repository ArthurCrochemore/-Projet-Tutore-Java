package fr.univtours.polytech.punchingmanagement.controller.crud;

import fr.univtours.polytech.punchingmanagement.controller.crud.CRUDResources.CRUDAction;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public abstract class AbstractReadController<T> extends AbstractCRUDController<T> {
    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    private T item;

    public T getItem() {
        return item;
    }

    public void initializeData(T item) {
        this.item = item;
    }

    public void initialize() {
        super.initialize();
        editButton.setOnKeyPressed(this::handleKeyPress);
        editButton.setOnAction(event -> openCRUDDialog(CRUDAction.UPDATE));
        deleteButton.setOnKeyPressed(this::handleKeyPress);
        deleteButton.setOnAction(event -> openCRUDDialog(CRUDAction.DELETE));
    }

    @Override
    protected void refresh() {
        // Update with override method
        initializeData(getItem());
    }
}
