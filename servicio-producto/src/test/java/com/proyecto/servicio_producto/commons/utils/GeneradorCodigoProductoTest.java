package com.proyecto.servicio_producto.commons.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneradorCodigoProductoTest {

    @Test
    void generateCodigoUnaPalabraTest() {
        String actual = GeneradorCodigoProducto.generateCodigo("Producto");
        String actual2 = GeneradorCodigoProducto.generateCodigo("A");

        assertEquals(8, actual.length());
        assertTrue(actual.matches("^PRO-[A-Z0-9]{4}$"));

        assertEquals(8, actual2.length());
        assertTrue(actual2.matches("^AXX-[A-Z0-9]{4}$"));

    }

    @Test
    void generateCodigoDosPalabrasTest() {
        String actual = GeneradorCodigoProducto.generateCodigo("Teclado numerico");

        assertEquals(8, actual.length());
        assertTrue(actual.matches("^TNX-[A-Z0-9]{4}$"));
    }

    @Test
    void generateCodigoTresOMasPalabrasTest() {
        String actual = GeneradorCodigoProducto.generateCodigo("Teclado numerico mecanico");
        String actual2 = GeneradorCodigoProducto.generateCodigo("Teclado numerico mecanico con mouse");

        assertEquals(8, actual.length());
        assertTrue(actual.matches("^TNM-[A-Z0-9]{4}$"));

        assertEquals(8, actual2.length());
        assertTrue(actual2.matches("^TNM-[A-Z0-9]{4}$"));

        assertNotEquals(actual, actual2);
    }

}