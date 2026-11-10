package co.edu.uniquindio.poo.parcial3.model;

public class PanaderoIndustrial extends Empleado implements IProductor {

    public static final String TIPO = "Panadero industrial";

    private static final int MINUTOS_PREPARACION = 20;
    private static final int PANES_POR_LOTE = 30;
    private static final int MINUTOS_TRADICIONAL = 25;
    private static final int MINUTOS_INTEGRAL = 35;

    public PanaderoIndustrial(String nombre, String documento, String codigo, int minutos) {
        super(nombre, documento, codigo, minutos);
    }

    @Override
    public int calcularMinutos(TipoPan tipo, int cantidad) {
        int lotes = (cantidad + PANES_POR_LOTE - 1) / PANES_POR_LOTE;
        int minutosPorLote;

        if(tipo == TipoPan.TRADICIONAL){
            minutosPorLote = MINUTOS_TRADICIONAL;
        }else {
            minutosPorLote = MINUTOS_INTEGRAL;
        }

        return MINUTOS_PREPARACION + lotes * minutosPorLote;
    }

    @Override
    public String getTipo() {
        return TIPO;
    }
}
