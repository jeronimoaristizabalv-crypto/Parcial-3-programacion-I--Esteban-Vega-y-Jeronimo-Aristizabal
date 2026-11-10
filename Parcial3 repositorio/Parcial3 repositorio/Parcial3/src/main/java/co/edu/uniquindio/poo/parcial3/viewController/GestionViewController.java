package co.edu.uniquindio.poo.parcial3.viewController;

import co.edu.uniquindio.poo.parcial3.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GestionViewController {

    private App app;
    private EmpleadoViewController empleadoViewController;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabEmpleados;

    @FXML
    private Tab tabPedidos;

    @FXML
    private void initialize() throws IOException {

        FXMLLoader loaderEmpleados = new FXMLLoader(
                getClass().getResource("/co/edu/uniquindio/poo/parcial3/crudEmpleado.fxml"));
        AnchorPane vistaEmpleados = loaderEmpleados.load();
        empleadoViewController = loaderEmpleados.getController();
        tabEmpleados.setContent(vistaEmpleados);

        FXMLLoader loaderPedidos = new FXMLLoader(
                getClass().getResource("/co/edu/uniquindio/poo/parcial3/crudPedido.fxml"));
        AnchorPane vistaPedidos = loaderPedidos.load();
        tabPedidos.setContent(vistaPedidos);

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, anterior, actual) -> {
            if (actual == tabEmpleados) {
                empleadoViewController.actualizarTabla();
            }
        });
    }

    public void setApp(App app) {
        this.app = app;
    }
}
