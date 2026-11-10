package co.edu.uniquindio.poo.parcial3.model;

import javafx.fxml.FXML;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class PanaderiaTest {

    Panaderia panaderia;
    private static final Logger LOG = Logger.getLogger(PanaderiaTest.class.getName());
    @BeforeEach
    public void setPanaderia() {
        panaderia = new Panaderia("El trigal");
    }

    //test 1
    @Test
    public void registrarPrueba(){
        LOG.info("Inicio de prueba registro...");
        PanaderoArtesanal artesanal = new PanaderoArtesanal("Juan","123","444",120);
        panaderia.registrarEmpleado(artesanal);
        int minutosArtesanal = artesanal.getMinutos();

        Pedido pedidoRegistrar = panaderia.registrarPedido("P01","test",TipoPan.TRADICIONAL,25);

        assertEquals(EstadoPedido.PENDIENTE,pedidoRegistrar.getEstado());
        assertEquals(25,pedidoRegistrar.getCantidad());
        assertEquals(minutosArtesanal,artesanal.getMinutos());
        LOG.info("Fin de prueba el pedido quedo como pendiente y no se altero la cantidad ni los minutos del empleado...");
    }
    //test 2
    @Test
    public void registrarCancelar(){

        LOG.info("Inicio de prueba registrar cancelar...");
        Pedido pedidoRegistrar = panaderia.registrarPedido("P01","test",TipoPan.TRADICIONAL,25);
        panaderia.cancelarPedido("P01");

        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {panaderia.registrarPedido("P01","test",TipoPan.TRADICIONAL,25);});
        System.out.println(excepcion);
    }

    //test 3
    @Test
    public void registrarPedidoCero(){
        LOG.info("Inicio de prueba registrar cero...");
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class,() -> {panaderia.registrarPedido("P01","test",TipoPan.TRADICIONAL,0);});

        System.out.println(excepcion);

    }

    //test 4
    @Test
    public void asignarTest1(){

        LOG.info("Inicio de prueba asignar 1...");
        PanaderoArtesanal artesanal = new PanaderoArtesanal("Ana","123","444",100);
        panaderia.registrarEmpleado(artesanal);

        PanaderoIndustrial industrial = new PanaderoIndustrial("Carlos","125","443",45);
        panaderia.registrarEmpleado(industrial);

        Pedido pedidoRegistrar = panaderia.registrarPedido("P01","test",TipoPan.TRADICIONAL,25);

        panaderia.asignarPedido("P01");
        String esperado = "443";
        assertEquals(esperado, pedidoRegistrar.getCodigoEmpleadoAsignado());
        LOG.info("Fin de prueba el pedido fue asignado a carlos, quedando con 0 minutos y ana quedo con 100...");
    }

    //test 5
    @Test
    public void asignarTest2(){
        LOG.info("Inicio de prueba asignar 2...");
        PanaderoArtesanal artesanal = new PanaderoArtesanal("Ana","123","444",80);
        panaderia.registrarEmpleado(artesanal);

        PanaderoArtesanal artesanal2 = new PanaderoArtesanal("Diana","132","424",80);
        panaderia.registrarEmpleado(artesanal2);

        PanaderoIndustrial industrial = new PanaderoIndustrial("Carlos","125","443",54);
        panaderia.registrarEmpleado(industrial);

        Pedido pedidoRegistrar = panaderia.registrarPedido("P01","test",TipoPan.INTEGRAL,13);
        panaderia.asignarPedido("P01");

        String esperado = "444";
        assertEquals(esperado, pedidoRegistrar.getCodigoEmpleadoAsignado());
        LOG.info("Fin de prueba el pedido fue asignado a ana pues carlos necesitaba 55min por tanto no puede y ana y diana si podian...");
    }

    //test 6
    @Test
    public void asignarTest3(){

        LOG.info("Inicio de prueba asignar 3...");
        Cajero cajero = new Cajero("Ana","123","444",120,3);
        panaderia.registrarEmpleado(cajero);

        PanaderoIndustrial industrial = new PanaderoIndustrial("Carlos","125","443",44);
        panaderia.registrarEmpleado(industrial);

        Pedido pedidoRegistrar = panaderia.registrarPedido("P01","test",TipoPan.TRADICIONAL,25);
        IllegalStateException excepcion = assertThrows(IllegalStateException.class,()->{panaderia.asignarPedido("P01");});
        System.out.println(excepcion);
    }

    //test 7
    @Test
    public void cancelarTest1(){
        LOG.info("Inicio de prueba cancelar 1...");
        PanaderoIndustrial industrial= new PanaderoIndustrial("Carlos", "666", "999", 120);
        panaderia.registrarEmpleado(industrial);

        Pedido pedido01= panaderia.registrarPedido("P01","test",TipoPan.TRADICIONAL,25);
        panaderia.asignarPedido("P01");
        Pedido pedido02= panaderia.registrarPedido("P02","test",TipoPan.TRADICIONAL,25);
        panaderia.asignarPedido("P02");

        panaderia.cancelarPedido("P01");

        assertEquals(75, industrial.getMinutos());
        assertEquals(EstadoPedido.CANCELADO, pedido01.getEstado());
        assertEquals(EstadoPedido.ASIGNADO, pedido02.getEstado());
        LOG.info("Fin de prueba cancelar 1...");
    }

    //test 8
    @Test
    public void cancelarTest2(){
        LOG.info("Inicio de prueba cancelar 2...");
        
        PanaderoArtesanal panaderoTest= new PanaderoArtesanal("Manuel","123","444",100);
        panaderia.registrarEmpleado(panaderoTest);

        Pedido pedidoTest= panaderia.registrarPedido("P01","test",TipoPan.INTEGRAL,25);
        panaderia.cancelarPedido("P01");

        assertEquals(EstadoPedido.CANCELADO, pedidoTest.getEstado());
        assertTrue(panaderia.buscarPedido("P01").isPresent());
        assertEquals(100, panaderoTest.getMinutos());
        LOG.info("Fin de prueba cancelar 2...");
    }

    //test 9
    @Test
    public void cancelarTest3() {
        LOG.info("Inicio de prueba cancelar 3...");

        PanaderoIndustrial panaderoTest = new PanaderoIndustrial("Robinson", "123", "444", 100);
        int minutosIniciales= panaderoTest.getMinutos();
        panaderia.registrarEmpleado(panaderoTest);

        Pedido pedidoTest= panaderia.registrarPedido("P01","test",TipoPan.INTEGRAL,25);
        panaderia.asignarPedido("P01");
        assertEquals(45, panaderoTest.getMinutos());
        panaderia.cancelarPedido("P01");
        assertEquals(minutosIniciales, panaderoTest.getMinutos());


        IllegalStateException excepcion = assertThrows(IllegalStateException.class,
                () -> panaderia.cancelarPedido("P01"));
        System.out.println(excepcion);

        assertEquals(minutosIniciales, panaderoTest.getMinutos());
        assertEquals(EstadoPedido.CANCELADO, pedidoTest.getEstado());
        LOG.info("Fin de prueba cancelar 3...");
    }
}
