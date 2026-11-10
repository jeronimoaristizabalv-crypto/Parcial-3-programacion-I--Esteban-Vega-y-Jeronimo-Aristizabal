package co.edu.uniquindio.poo.parcial3.viewController;

import co.edu.uniquindio.poo.parcial3.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {

    private App app;

    @FXML
    private Button btnOpenGestion;

    @FXML
    void openGestion() {
        app.openGestion();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
