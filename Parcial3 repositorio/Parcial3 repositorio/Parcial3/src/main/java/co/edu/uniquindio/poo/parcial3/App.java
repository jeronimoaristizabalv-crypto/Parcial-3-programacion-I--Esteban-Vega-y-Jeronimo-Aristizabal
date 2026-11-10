package co.edu.uniquindio.poo.parcial3;

import co.edu.uniquindio.poo.parcial3.model.Cajero;
import co.edu.uniquindio.poo.parcial3.model.PanaderoArtesanal;
import co.edu.uniquindio.poo.parcial3.model.PanaderoIndustrial;
import co.edu.uniquindio.poo.parcial3.model.Panaderia;
import co.edu.uniquindio.poo.parcial3.viewController.GestionViewController;
import co.edu.uniquindio.poo.parcial3.viewController.PrimaryController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private Stage primaryStage;
    public static Panaderia panaderia = new Panaderia("Panadería El Trigal");

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestión panadería El Trigal");
        openViewPrincipal();
    }

    private void openViewPrincipal() {
        inicializarData();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(
                    "/co/edu/uniquindio/poo/parcial3/primary.fxml"));
            VBox rootLayout = (VBox) loader.load();
            PrimaryController primaryController = loader.getController();
            primaryController.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public void openGestion() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(
                    "/co/edu/uniquindio/poo/parcial3/gestion.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            GestionViewController gestionViewController = loader.getController();
            gestionViewController.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void inicializarData() {
        panaderia.registrarEmpleado(new PanaderoArtesanal("Ana Gómez", "1001", "E01", 480));
        panaderia.registrarEmpleado(new PanaderoIndustrial("Carlos Ruiz", "1002", "E02", 480));
        panaderia.registrarEmpleado(new Cajero("Lucía Torres", "1003", "E03", 480, 1));
    }
}
