package co.edu.uniquindio.poo.parcial3.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Panaderia {

    private String nombre;
    private List<Empleado> listaEmpleados = new ArrayList<>();
    private List<Pedido> listaPedidos = new ArrayList<>();

    public Panaderia(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public boolean registrarEmpleado(Empleado empleado) {
        if (buscarEmpleado(empleado.getCodigo()).isPresent()) {
            return false;
        }
        listaEmpleados.add(empleado);
        return true;
    }

    public boolean eliminarEmpleado(String codigo) {
        Optional<Empleado> empleado = buscarEmpleado(codigo);
        if (empleado.isEmpty()) {
            return false;
        }

        boolean tienePedidosAsignados = listaPedidos.stream()
                .anyMatch(p -> p.getEstado() == EstadoPedido.ASIGNADO
                        && codigo.equals(p.getCodigoEmpleadoAsignado()));
        if (tienePedidosAsignados) {
            return false;
        }
        listaEmpleados.remove(empleado.get());
        return true;
    }

    public boolean actualizarEmpleado(String codigo, Empleado nuevo) {
        Optional<Empleado> opt = buscarEmpleado(codigo);
        if (opt.isEmpty()) {
            return false;
        }
        Empleado empleado = opt.get();
        empleado.setNombre(nuevo.getNombre());
        empleado.setDocumento(nuevo.getDocumento());
        empleado.setMinutos(nuevo.getMinutos());
        if (empleado instanceof Cajero && nuevo instanceof Cajero) {
            ((Cajero) empleado).setCaja(((Cajero) nuevo).getCaja());
        }
        return true;
    }

    public Optional<Empleado> buscarEmpleado(String codigo) {
        return listaEmpleados.stream()
                .filter(e -> e.getCodigo().equals(codigo))
                .findFirst();
    }

    public Optional<Pedido> buscarPedido(String codigo) {
        return listaPedidos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst();
    }

    public Pedido registrarPedido(String codigo, String nombreCliente, TipoPan tipoPan, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de panes debe ser mayor que cero");
        }
        if (buscarPedido(codigo).isPresent()) {
            throw new IllegalArgumentException("Ya existe un pedido con el código " + codigo);
        }
        Pedido pedido = new Pedido(codigo, nombreCliente, tipoPan, cantidad);
        listaPedidos.add(pedido);
        return pedido;
    }

    public Empleado asignarPedido(String codigoPedido) {
            Pedido pedido = buscarPedido(codigoPedido)
                .orElseThrow(() -> new IllegalArgumentException("No existe el pedido " + codigoPedido));

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("El pedido " + codigoPedido + " no está pendiente");
        }

        Empleado elegido = null;
        int menorTiempo = 0;

        for (Empleado ayuda : listaEmpleados) {
            if (ayuda instanceof IProductor) {
                int minutos = ((IProductor) ayuda).calcularMinutos(pedido.getTipoPan(), pedido.getCantidad());
                if (ayuda.tieneTiempoPara(minutos) && (elegido == null || minutos < menorTiempo)) {
                    elegido = ayuda;
                    menorTiempo = minutos;
                }
            }
        }

        if (elegido == null) {
            throw new IllegalStateException("Ningún panadero tiene tiempo suficiente o no se encuentran "+
                                          "\npanaderos en el momento para el pedido "+ codigoPedido);
        }

        elegido.reservarMinutos(menorTiempo);
        pedido.asignar(elegido.getCodigo(), menorTiempo);
        return elegido;
    }

    public void cancelarPedido(String codigoPedido) {
        Pedido pedido = buscarPedido(codigoPedido)
                .orElseThrow(() -> new IllegalArgumentException("No existe pedido con codigo: " + codigoPedido));

        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new IllegalStateException("El pedido " + codigoPedido + " ya está cancelado");
        }

        if (pedido.getEstado() == EstadoPedido.ASIGNADO) {
            Optional<Empleado> empleado = buscarEmpleado(pedido.getCodigoEmpleadoAsignado());
            if(empleado.isPresent()){
                empleado.get().devolverMinutos(pedido.getMinutosReservados());
            }
        }
        pedido.estadoCancelar();
    }
}
