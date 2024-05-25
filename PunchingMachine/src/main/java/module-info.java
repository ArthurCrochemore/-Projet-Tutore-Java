module PunchingMachine {
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive PunchingCommon;

    opens fr.univtours.polytech.punchingmachine.controller to javafx.fxml;

    exports fr.univtours.polytech.punchingmachine;
    exports fr.univtours.polytech.punchingmachine.controller;
}


