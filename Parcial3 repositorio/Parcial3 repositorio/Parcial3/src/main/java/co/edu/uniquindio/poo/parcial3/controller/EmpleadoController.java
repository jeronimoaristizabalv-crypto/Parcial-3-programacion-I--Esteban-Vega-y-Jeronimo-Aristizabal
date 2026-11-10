package co.edu.uniquindio.poo.parcial3.controller;

import co.edu.uniquindio.poo.parcial3.model.Empleado;
import co.edu.uniquindio.poo.parcial3.model.Panaderia;

import java.util.Collection;

public class EmpleadoController {

    private final Panaderia panaderia;

    public EmpleadoController(Panaderia panaderia) {
        this.panaderia = panaderia;
    }

    public boolean registrarEmpleado(Empleado empleado) {
        return panaderia.registrarEmpleado(empleado);
    }

    public Collection<Empleado> getListaEmpleados() {
        return panaderia.getListaEmpleados();
    }

    public boolean eliminarEmpleado(String codigo) {
        return panaderia.eliminarEmpleado(codigo);
    }

    public boolean actualizarEmpleado(String codigo, Empleado empleado) {
        return panaderia.actualizarEmpleado(codigo, empleado);
    }
}
