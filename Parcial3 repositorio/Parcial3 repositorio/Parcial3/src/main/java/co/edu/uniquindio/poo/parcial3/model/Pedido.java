package co.edu.uniquindio.poo.parcial3.model;

public class Pedido {

    private String codigo;
    private String nombreCliente;
    private TipoPan tipoPan;
    private int cantidad;
    private EstadoPedido estado;
    private String codigoEmpleadoAsignado;
    private int minutosReservados;

    public Pedido(String codigo, String nombreCliente, TipoPan tipoPan, int cantidad) {
        this.codigo = codigo;
        this.nombreCliente = nombreCliente;
        this.tipoPan = tipoPan;
        this.cantidad = cantidad;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public TipoPan getTipoPan() {
        return tipoPan;
    }

    public int getCantidad() {
        return cantidad;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public String getCodigoEmpleadoAsignado() {
        return codigoEmpleadoAsignado;
    }

    public int getMinutosReservados() {
        return minutosReservados;
    }

    public void asignar(String codigoEmpleado, int minutos) {
        this.codigoEmpleadoAsignado = codigoEmpleado;
        this.minutosReservados = minutos;
        this.estado = EstadoPedido.ASIGNADO;
    }

    public void estadoCancelar() {
        this.estado = EstadoPedido.CANCELADO;
    }
}
