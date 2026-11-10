package co.edu.uniquindio.poo.parcial3.viewController;

import co.edu.uniquindio.poo.parcial3.App;
import co.edu.uniquindio.poo.parcial3.controller.EmpleadoController;
import co.edu.uniquindio.poo.parcial3.model.Cajero;
import co.edu.uniquindio.poo.parcial3.model.Empleado;
import co.edu.uniquindio.poo.parcial3.model.PanaderoArtesanal;
import co.edu.uniquindio.poo.parcial3.model.PanaderoIndustrial;
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

public class EmpleadoViewController {

    private EmpleadoController empleadoController;
    private final ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();
    private Empleado selectedEmpleado;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtMinutos;

    @FXML
    private TextField txtCaja;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private TableView<Empleado> tblEmpleados;

    @FXML
    private TableColumn<Empleado, String> tbcNombre;

    @FXML
    private TableColumn<Empleado, String> tbcDocumento;

    @FXML
    private TableColumn<Empleado, String> tbcCodigo;

    @FXML
    private TableColumn<Empleado, Integer> tbcMinutos;

    @FXML
    private TableColumn<Empleado, String> tbcTipo;

    @FXML
    private TableColumn<Empleado, String> tbcCaja;

    @FXML
    void initialize() {
        empleadoController = new EmpleadoController(App.panaderia);

        cbTipo.getItems().addAll(PanaderoArtesanal.TIPO, PanaderoIndustrial.TIPO, Cajero.TIPO);
        // La caja solo aplica a los cajeros
        cbTipo.setOnAction(e -> txtCaja.setDisable(!Cajero.TIPO.equals(cbTipo.getValue())));
        txtCaja.setDisable(true);

        initDataBinding();
        listaEmpleados.addAll(empleadoController.getListaEmpleados());
        tblEmpleados.setItems(listaEmpleados);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tbcDocumento.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDocumento()));
        tbcCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        tbcMinutos.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getMinutos()));
        tbcTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo()));
        tbcCaja.setCellValueFactory(c -> {
            Empleado e = c.getValue();
            String caja = (e instanceof Cajero) ? String.valueOf(((Cajero) e).getCaja()) : "-";
            return new SimpleStringProperty(caja);
        });
    }

    private void listenerSelection() {
        tblEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, anterior, actual) -> {
            selectedEmpleado = actual;
            if (actual != null) {
                mostrarEmpleado(actual);
            }
        });
    }

    private void mostrarEmpleado(Empleado empleado) {
        txtNombre.setText(empleado.getNombre());
        txtDocumento.setText(empleado.getDocumento());
        txtCodigo.setText(empleado.getCodigo());
        txtMinutos.setText(String.valueOf(empleado.getMinutos()));
        cbTipo.setValue(empleado.getTipo());
        if (empleado instanceof Cajero) {
            txtCaja.setText(String.valueOf(((Cajero) empleado).getCaja()));
        } else {
            txtCaja.clear();
        }
        // El código y el tipo no se pueden cambiar al actualizar
        txtCodigo.setDisable(true);
        cbTipo.setDisable(true);
    }

    @FXML
    void registrarEmpleado() {
        try {
            Empleado empleado = buildEmpleado();
            if (empleadoController.registrarEmpleado(empleado)) {
                listaEmpleados.add(empleado);
                limpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Ya existe un empleado con el código " + empleado.getCodigo());
            }
        } catch (IllegalArgumentException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, ex.getMessage());
        }
    }

    @FXML
    void actualizarEmpleado() {
        if (selectedEmpleado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un empleado de la tabla");
            return;
        }
        try {
            Empleado nuevo = buildEmpleado();
            if (empleadoController.actualizarEmpleado(selectedEmpleado.getCodigo(), nuevo)) {
                tblEmpleados.refresh();
                limpiarCampos();
            }
        } catch (IllegalArgumentException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, ex.getMessage());
        }
    }

    @FXML
    void eliminarEmpleado() {
        if (selectedEmpleado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleccione un empleado de la tabla");
            return;
        }
        if (empleadoController.eliminarEmpleado(selectedEmpleado.getCodigo())) {
            listaEmpleados.remove(selectedEmpleado);
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "No se puede eliminar: el empleado tiene pedidos asignados");
        }
    }

    @FXML
    void limpiarCampos() {
        tblEmpleados.getSelectionModel().clearSelection();
        selectedEmpleado = null;
        txtNombre.clear();
        txtDocumento.clear();
        txtCodigo.clear();
        txtMinutos.clear();
        txtCaja.clear();
        cbTipo.setValue(null);
        txtCodigo.setDisable(false);
        cbTipo.setDisable(false);
        txtCaja.setDisable(true);
    }

    public void actualizarTabla() {
        tblEmpleados.refresh();
    }

    private Empleado buildEmpleado() {
        String nombre = txtNombre.getText().trim();
        String documento = txtDocumento.getText().trim();
        String codigo = txtCodigo.getText().trim();
        String tipo = cbTipo.getValue();

        if (nombre.isEmpty() || documento.isEmpty() || codigo.isEmpty() || tipo == null) {
            throw new IllegalArgumentException("Complete nombre, documento, código y tipo de empleado");
        }

        int minutos = leerEntero(txtMinutos.getText(), "Los minutos");
        if (minutos < 0) {
            throw new IllegalArgumentException("Los minutos no pueden ser negativos");
        }

        if (PanaderoArtesanal.TIPO.equals(tipo)) {
            return new PanaderoArtesanal(nombre, documento, codigo, minutos);
        }
        if (PanaderoIndustrial.TIPO.equals(tipo)) {
            return new PanaderoIndustrial(nombre, documento, codigo, minutos);
        }
        int caja = leerEntero(txtCaja.getText(), "La caja");
        return new Cajero(nombre, documento, codigo, minutos, caja);
    }

    private int leerEntero(String texto, String campo) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(campo + " debe ser un número entero");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
