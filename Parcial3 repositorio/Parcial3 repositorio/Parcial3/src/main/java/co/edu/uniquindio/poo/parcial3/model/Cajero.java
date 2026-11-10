package co.edu.uniquindio.poo.parcial3.model;

public class Cajero extends Empleado {

    public static final String TIPO = "Cajero";

    private int caja;

    public Cajero(String nombre, String documento, String codigo, int minutos, int caja) {
        super(nombre, documento, codigo, minutos);
        this.caja = caja;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int caja) {
        this.caja = caja;
    }

    @Override
    public String getTipo() {
        return TIPO;
    }
}
