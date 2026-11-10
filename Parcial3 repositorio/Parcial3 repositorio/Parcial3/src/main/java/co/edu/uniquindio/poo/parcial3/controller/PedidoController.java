package co.edu.uniquindio.poo.parcial3.controller;

import co.edu.uniquindio.poo.parcial3.model.Empleado;
import co.edu.uniquindio.poo.parcial3.model.Panaderia;
import co.edu.uniquindio.poo.parcial3.model.Pedido;
import co.edu.uniquindio.poo.parcial3.model.TipoPan;

import java.util.Collection;

public class PedidoController {

    private final Panaderia panaderia;

    public PedidoController(Panaderia panaderia) {
        this.panaderia = panaderia;
    }

    public Pedido registrarPedido(String codigo, String nombreCliente, TipoPan tipoPan, int cantidad) {
        return panaderia.registrarPedido(codigo, nombreCliente, tipoPan, cantidad);
    }

    public Collection<Pedido> getListaPedidos() {
        return panaderia.getListaPedidos();
    }

    public Empleado asignarPedido(String codigo) {
        return panaderia.asignarPedido(codigo);
    }

    public void cancelarPedido(String codigo) {
        panaderia.cancelarPedido(codigo);
    }
}
