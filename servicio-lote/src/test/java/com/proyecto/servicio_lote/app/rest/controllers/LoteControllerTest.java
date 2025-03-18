package com.proyecto.servicio_lote.app.rest.controllers;

import com.proyecto.servicio_lote.app.rest.request.LoteRequest;
import com.proyecto.servicio_lote.app.rest.response.LoteResponse;
import com.proyecto.servicio_lote.app.rest.response.ProveedorResponse;
import com.proyecto.servicio_lote.domain.services.LoteService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoteControllerTest {

    private static final Long LOTE_ID = 1L;
    private static final Long PROVEEDOR_ID = 1L;
    private static final Long PRODUCTO_ID = 1L;
    private static final BigDecimal COSTO_COMPRA = BigDecimal.valueOf(10);
    private static final Integer CANTIDAD = 50;
    private static final LocalDate FECHA_EXPIRACION = LocalDate.of(2025, 12, 3);
    private static final LocalDate FECHA_ADQUISICION = LocalDate.now();

    private static final LoteRequest LOTE_REQUEST = new LoteRequest(
            PROVEEDOR_ID,
            PRODUCTO_ID,
            COSTO_COMPRA,
            CANTIDAD,
            FECHA_EXPIRACION
    );

    private static final ProveedorResponse PROVEEDOR_RESPONSE = new ProveedorResponse(
            PROVEEDOR_ID,
            "Proveedor 1",
            "Direccion de proveedor 1"
    );

    private static final LoteResponse LOTE_RESPONSE = new LoteResponse(
            LOTE_ID,
            PRODUCTO_ID,
            CANTIDAD,
            COSTO_COMPRA,
            FECHA_ADQUISICION,
            FECHA_EXPIRACION,
            PROVEEDOR_RESPONSE
    );


    @Mock
    private LoteService loteService;

    @InjectMocks
    private LoteController loteController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void registrarLote() {
        Mockito.when(loteService.registrarLote(LOTE_REQUEST)).thenReturn(LOTE_RESPONSE);

        ResponseEntity<LoteResponse> actual = loteController.registrarLote(LOTE_REQUEST);

        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(LOTE_ID, actual.getBody().id());
        assertEquals(PRODUCTO_ID, actual.getBody().productoId());
        assertEquals(CANTIDAD, actual.getBody().cantidad());
        assertEquals(COSTO_COMPRA, actual.getBody().costoCompra());
        assertEquals(FECHA_EXPIRACION, actual.getBody().fechaExpiracion());
        assertEquals(FECHA_ADQUISICION, actual.getBody().fechaAdquisicion());
        assertEquals(PROVEEDOR_ID, actual.getBody().proveedor().id());
    }

    @Test
    void obtenerLotesPorIdProductoOrdenadosPorVencimiento() {
    }

    @Test
    void actualizarStock() {
    }
}