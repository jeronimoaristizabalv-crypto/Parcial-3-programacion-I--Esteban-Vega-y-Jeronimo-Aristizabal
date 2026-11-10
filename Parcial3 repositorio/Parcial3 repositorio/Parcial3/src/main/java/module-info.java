module co.edu.uniquindio.poo.parcial3 {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    exports co.edu.uniquindio.poo.parcial3;
    exports co.edu.uniquindio.poo.parcial3.controller;
    exports co.edu.uniquindio.poo.parcial3.model;

    opens co.edu.uniquindio.poo.parcial3.viewController to javafx.fxml;
    opens co.edu.uniquindio.poo.parcial3.model to javafx.base, javafx.fxml;
}
