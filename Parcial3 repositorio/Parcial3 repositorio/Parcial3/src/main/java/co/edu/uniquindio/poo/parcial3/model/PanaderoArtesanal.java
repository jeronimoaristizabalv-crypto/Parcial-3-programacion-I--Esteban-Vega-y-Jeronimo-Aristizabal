package co.edu.uniquindio.poo.parcial3.model;

public class PanaderoArtesanal extends Empleado implements IProductor {

    public static final String TIPO = "Panadero artesanal";

    private static final int PANES_POR_BANDEJA = 12;
    private static final int MINUTOS_TRADICIONAL = 30;
    private static final int MINUTOS_INTEGRAL = 40;

    public PanaderoArtesanal(String nombre, String documento, String codigo, int minutos) {
        super(nombre, documento, codigo, minutos);
    }

    @Override
    public int calcularMinutos(TipoPan tipo, int cantidad) {
        int bandejas = (cantidad + PANES_POR_BANDEJA - 1) / PANES_POR_BANDEJA;
        int minutosPorBandeja;

        if(tipo == TipoPan.TRADICIONAL){
            minutosPorBandeja = MINUTOS_TRADICIONAL;
        }else {
            minutosPorBandeja = MINUTOS_INTEGRAL;
        }

        return bandejas * minutosPorBandeja;
    }

    @Override
    public String getTipo() {
        return TIPO;
    }
}
