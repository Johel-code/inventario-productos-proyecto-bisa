package com.proyecto.servicio_venta.app.rest.controllers;

import com.proyecto.servicio_venta.app.rest.request.DetalleVentaRequest;
import com.proyecto.servicio_venta.app.rest.request.VentaRequest;
import com.proyecto.servicio_venta.app.rest.response.DetalleVentaResponse;
import com.proyecto.servicio_venta.app.rest.response.VentaResponse;
import com.proyecto.servicio_venta.domain.services.VentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class VentaControllerTest {

    private static final Long PRODUCTO_ID = 1L;
    private static final Integer CANTIDAD = 50;
    private static final BigDecimal PRECIO_UNITARIO = BigDecimal.TEN;

    private static final DetalleVentaRequest DETALLE_VENTA_REQUEST = new DetalleVentaRequest(
            PRODUCTO_ID,
            CANTIDAD,
            PRECIO_UNITARIO
    );

    private static final DetalleVentaResponse DETALLE_VENTA_RESPONSE = new DetalleVentaResponse(
            PRODUCTO_ID,
            CANTIDAD,
            PRECIO_UNITARIO,
            BigDecimal.TEN
    );

    private static final List<DetalleVentaRequest> LIST_DETALLE_VENTA = List.of(DETALLE_VENTA_REQUEST);

    private static final VentaRequest VENTA_REQUEST = new VentaRequest(
            LIST_DETALLE_VENTA
    );

    private static final VentaResponse VENTA_RESPONSE = new VentaResponse(
            1L,
            LocalDate.now(),
            BigDecimal.TEN,
            List.of(DETALLE_VENTA_RESPONSE)
    );

    @Mock
    VentaService ventaService;

    @InjectMocks
    VentaController ventaController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void realizarVenta() {
        Mockito.when(ventaService.registrarVenta(VENTA_REQUEST)).thenReturn(VENTA_RESPONSE);

        ResponseEntity<VentaResponse> actual = ventaController.realizarVenta(VENTA_REQUEST);

        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(LocalDate.now(), actual.getBody().fecha());
        assertEquals(1, actual.getBody().detalleVenta().size());
        assertEquals(DETALLE_VENTA_REQUEST.productoId(), actual.getBody().detalleVenta().getFirst().productoId());
    }
}