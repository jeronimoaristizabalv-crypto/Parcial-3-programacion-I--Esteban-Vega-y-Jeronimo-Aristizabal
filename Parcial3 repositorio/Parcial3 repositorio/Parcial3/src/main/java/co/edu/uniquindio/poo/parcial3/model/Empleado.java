package co.edu.uniquindio.poo.parcial3.model;

public abstract class Empleado extends Persona {

    private String codigo;
    private int minutos;

    public Empleado(String nombre, String documento, String codigo, int minutos) {
        super(nombre, documento);
        this.codigo = codigo;
        this.minutos = minutos;
    }

    public boolean tieneTiempoPara(int minutosNecesarios) {
        return minutos >= minutosNecesarios;
    }

    public void reservarMinutos(int cantidad) {
        minutos -= cantidad;
    }

    public void devolverMinutos(int cantidad) {
        minutos += cantidad;
    }

    public abstract String getTipo();

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
}
