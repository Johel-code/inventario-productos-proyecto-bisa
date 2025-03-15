package com.proyecto.servicio_producto.app.rest.controllers;

import com.proyecto.servicio_producto.app.rest.request.ProductoRequest;
import com.proyecto.servicio_producto.app.rest.response.ProductoResponse;
import com.proyecto.servicio_producto.commons.exceptions.IdNotFoudException;
import com.proyecto.servicio_producto.domain.services.impl.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    private static final Long PRODUCTO_ID = 1L;
    private static final String CODIGO_PRODUCTO = "TNM-3921";
    private static final String NOMBRE = "Teclado numerico mecanico";
    private static final BigDecimal COSTO_COMPRA = BigDecimal.valueOf(10);
    private static final BigDecimal PRECIO_VENTA = BigDecimal.valueOf(12);
    private static final Integer CANTIDAD_STOCK = 1000;
    private static final Integer MIN_STOCK = 50;
    private static final Double PORCENTAJE_GANANCIA = 0.2;
    private static final Long CATEGORIA_ID = 1L;

    private static final ProductoResponse PRODUCTO_RESPONSE = new ProductoResponse(
            PRODUCTO_ID,
            CODIGO_PRODUCTO,
            NOMBRE,
            COSTO_COMPRA,
            PRECIO_VENTA,
            CANTIDAD_STOCK,
            MIN_STOCK,
            PORCENTAJE_GANANCIA,
            CATEGORIA_ID
    );

    private static final ProductoRequest PRODUCTO_REQUEST = new ProductoRequest(
            NOMBRE,
            COSTO_COMPRA,
            MIN_STOCK,
            PORCENTAJE_GANANCIA,
            CATEGORIA_ID
    );

    private static final List<ProductoResponse> PRODUCTOS = Arrays.asList(PRODUCTO_RESPONSE);

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void mostrarProductosTest() {
        when(productoService.mostrarTodo()).thenReturn(PRODUCTOS);

        ResponseEntity<List<ProductoResponse>> actual = productoController.mostrarProductos();
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertFalse(actual.getBody().isEmpty());
        assertEquals(1, actual.getBody().size());
        assertEquals(PRODUCTOS, actual.getBody());
    }

    @Test
    void mostrarProductoPorIdTest() {
        when(productoService.mostrarPorId(PRODUCTO_ID)).thenReturn(PRODUCTO_RESPONSE);

        ResponseEntity<ProductoResponse> actual = productoController.mostrarProductoPorId(PRODUCTO_ID);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(PRODUCTO_RESPONSE, actual.getBody());
        assertEquals(PRODUCTO_ID, actual.getBody().id());
        assertEquals(CODIGO_PRODUCTO,actual.getBody().codigoProducto());
        assertEquals(COSTO_COMPRA,actual.getBody().costoCompra());
        assertEquals(PRECIO_VENTA,actual.getBody().precioVenta());
        assertEquals(CANTIDAD_STOCK,actual.getBody().cantidadStock());
        assertEquals(MIN_STOCK,actual.getBody().minStock());
        assertEquals(PORCENTAJE_GANANCIA,actual.getBody().porcentajeGanancia());
    }

    @Test
    void mostrarProductoPorIdNotFoundTest() {
        when(productoService.mostrarPorId(PRODUCTO_ID)).thenThrow(new IdNotFoudException("Producto"));

        assertThrows(IdNotFoudException.class, () -> productoController.mostrarProductoPorId(PRODUCTO_ID));
    }

    @Test
    void crearProductoTest() {
        when(productoService.crear(PRODUCTO_REQUEST)).thenReturn(PRODUCTO_RESPONSE);

        ResponseEntity<ProductoResponse> actual = productoController.crearProducto(PRODUCTO_REQUEST);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED, actual.getStatusCode()),
                () -> assertEquals(PRODUCTO_RESPONSE, actual.getBody()),
                () -> assertEquals(PRODUCTO_ID, actual.getBody().id()),
                () -> assertEquals(CODIGO_PRODUCTO,actual.getBody().codigoProducto()),
                () -> assertEquals(COSTO_COMPRA,actual.getBody().costoCompra()),
                () -> assertEquals(PRECIO_VENTA,actual.getBody().precioVenta()),
                () -> assertEquals(CANTIDAD_STOCK,actual.getBody().cantidadStock()),
                () -> assertEquals(MIN_STOCK,actual.getBody().minStock())
        );
    }

    @Test
    void actualizarProducto() {
    }

    @Test
    void eliminarProducto() {
    }

    @Test
    void actualizarCostoCompra() {
    }
}