module PunchingManagement {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires transitive PunchingCommon;

    opens fr.univtours.polytech.punchingmanagement.controller to javafx.fxml;
    opens fr.univtours.polytech.punchingmanagement.controller.crud to javafx.fxml;
    opens fr.univtours.polytech.punchingmanagement.controller.communication to javafx.fxml;

    exports fr.univtours.polytech.punchingmanagement;
    exports fr.univtours.polytech.punchingmanagement.model;
    exports fr.univtours.polytech.punchingmanagement.controller.communication;
}
