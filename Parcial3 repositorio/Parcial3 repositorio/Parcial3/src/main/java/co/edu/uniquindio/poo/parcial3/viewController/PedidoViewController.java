package co.edu.uniquindio.poo.parcial3.viewController;

import co.edu.uniquindio.poo.parcial3.App;
import co.edu.uniquindio.poo.parcial3.controller.PedidoController;
import co.edu.uniquindio.poo.parcial3.model.Empleado;
import co.edu.uniquindio.poo.parcial3.model.EstadoPedido;
import co.edu.uniquindio.poo.parcial3.model.Pedido;
import co.edu.uniquindio.poo.parcial3.model.TipoPan;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.stream.Collectors;

public class PedidoViewController {

    private PedidoController pedidoController;
    private final ObservableList<Pedido> listaPedidos = FXCollections.observableArrayList();
    private Pedido selectedPedido;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombreCliente;

    @FXML
    private TextField txtCantidad;

    @FXML
    private ComboBox<TipoPan> cbTipoPan;

    @FXML
    private ComboBox<EstadoPedido> cbEstado;

    @FXML
    private TableView<Pedido> tblPedidos;

    @FXML
    private TableColumn<Pedido, String> tbcCodigo;

    @FXML
    private TableColumn<Pedido, String> tbcNombreCliente;

    @FXML
    private TableColumn<Pedido, TipoPan> tbcTipoPan;

    @FXML
    private TableColumn<Pedido, Integer> tbcCantidad;

    @FXML
    private TableColumn<Pedido, EstadoPedido> tbcEstado;

    @FXML
    private TableColumn<Pedido, String> tbcCodigoEmpleadoAsignado;

    @FXML
    private TableColumn<Pedido, Integer> tbcMinutosReservados;

    @FXML
    void initialize() {
        pedidoController = new PedidoController(App.panaderia);

        // Los combos se llenan directamente con los valores de los enums
        cbTipoPan.getItems().addAll(TipoPan.values());
        cbEstado.getItems().addAll(EstadoPedido.values());

        initDataBinding();
        tblPedidos.setItems(listaPedidos);
        cargarPedidos();

        tblPedidos.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, actual) -> selectedPedido = actual);
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        tbcNombreCliente.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombreCliente()));
        tbcTipoPan.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getTipoPan()));
        tbcCantidad.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getCantidad()));
        tbcEstado.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getEstado()));
        tbcCodigoEmpleadoAsignado.setCellValueFactory(
                c -> new SimpleStringProperty(c.getValue().getCodigoEmpleadoAsignado()));
        tbcMinutosReservados.setCellValueFactory(
                c -> new SimpleObjectProperty<>(c.getValue().getMinutosReservados()));
    }

    // Recarga la tabla desde el modelo, aplicando el filtro de estado si hay uno elegido
    private void cargarPedidos() {
        EstadoPedido filtro = cbEstado.getValue();
        listaPedidos.setAll(pedidoController.getListaPedidos().stream()
                .filter(p -> filtro == null || p.getEstado() == filtro)
                .collect(Collectors.toList()));
    }

    @FXML
    void registrarPedido() {
        String codigo = txtCodigo.getText().trim();
        String nombreCliente = txtNombreCliente.getText().trim();
        TipoPan tipoPan = cbTipoPan.getValue();

        if (codigo.isEmpty() || nombreCliente.isEmpty() || tipoPan == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Complete código, cliente y tipo de pan");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, "La cantidad debe ser un número entero");
            return;
        }

        try {
            pedidoController.registrarPedido(codigo, nombreCliente, tipoPan, cantidad);
            cargarPedidos();
            limpiarCampos();
        } catch (IllegalArgumentException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, ex.getMessage());
        }
    }

    @FXML
    void asignarPedido() {
        if (selectedPedido == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un pedido de la tabla");
            return;
        }
        try {
            Empleado empleado = pedidoController.asignarPedido(selectedPedido.getCodigo());
            mostrarAlerta(Alert.AlertType.INFORMATION, "Pedido " + selectedPedido.getCodigo()
                    + " asignado a " + empleado.getNombre()
                    + " (" + selectedPedido.getMinutosReservados() + " minutos reservados)");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, ex.getMessage());
        }
        cargarPedidos();
    }

    @FXML
    void cancelarPedido() {
        if (selectedPedido == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un pedido de la tabla");
            return;
        }
        try {
            pedidoController.cancelarPedido(selectedPedido.getCodigo());
        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, ex.getMessage());
        }
        cargarPedidos();
    }

    @FXML
    void filtrarPorEstado() {
        cargarPedidos();
    }

    @FXML
    void verTodos() {
        cbEstado.setValue(null);
        cargarPedidos();
    }

    @FXML
    void limpiarCampos() {
        tblPedidos.getSelectionModel().clearSelection();
        selectedPedido = null;
        txtCodigo.clear();
        txtNombreCliente.clear();
        txtCantidad.clear();
        cbTipoPan.setValue(null);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
