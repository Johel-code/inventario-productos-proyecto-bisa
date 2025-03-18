package com.proyecto.servicio_lote.domain.services;

import com.proyecto.servicio_lote.app.rest.request.LoteRequest;
import com.proyecto.servicio_lote.app.rest.response.LoteResponse;
import com.proyecto.servicio_lote.app.rest.response.ProveedorResponse;
import com.proyecto.servicio_lote.common.enums.TipoMovimiento;
import com.proyecto.servicio_lote.common.exceptions.IdNotFoudException;
import com.proyecto.servicio_lote.domain.models.Kardex;
import com.proyecto.servicio_lote.domain.models.Lote;
import com.proyecto.servicio_lote.domain.models.Producto;
import com.proyecto.servicio_lote.domain.models.Proveedor;
import com.proyecto.servicio_lote.domain.repositories.KardexRepository;
import com.proyecto.servicio_lote.domain.repositories.LoteRepository;
import com.proyecto.servicio_lote.domain.repositories.ProductoRepository;
import com.proyecto.servicio_lote.domain.repositories.ProveedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LoteServiceTest {

    private static final Long LOTE_ID = 1L;
    private static final Long PROVEEDOR_ID = 1L;
    private static final Long PRODUCTO_ID = 1L;
    private static final BigDecimal COSTO_COMPRA = BigDecimal.valueOf(10);
    private static final BigDecimal PRECIO_VENTA = BigDecimal.valueOf(12);
    private static final Integer CANTIDAD = 50;
    private static final LocalDate FECHA_EXPIRACION = LocalDate.of(2025, 12, 3);
    private static final LocalDate FECHA_ADQUISICION = LocalDate.now();
    private static final String CODIGO_PRODUCTO = "TNM-3921";

    private static final LoteRequest LOTE_REQUEST = new LoteRequest(
            PROVEEDOR_ID,
            PRODUCTO_ID,
            COSTO_COMPRA,
            CANTIDAD,
            FECHA_EXPIRACION
    );

    private static final Proveedor PROVEEDOR = Proveedor.builder()
            .id(PROVEEDOR_ID)
            .nombre("Proveedor 1")
            .direccion("Direccion de proveedor 1")
            .build();

    private static final Producto PRODUCTO = Producto.builder()
            .id(PRODUCTO_ID)
            .codigoProducto(CODIGO_PRODUCTO)
            .nombre("Producto1")
            .costoCompra(COSTO_COMPRA)
            .precioVenta(PRECIO_VENTA)
            .minStock(50)
            .porcentajeGanancia(0.2)
            .lotes(new ArrayList<>())
            .build();

    private static final ProveedorResponse PROVEEDOR_RESPONSE = new ProveedorResponse(
            PROVEEDOR_ID,
            "Proveedor 1",
            "Direccion de proveedor 1"
    );

    private static final Lote LOTE = new Lote(
            LOTE_ID,
            PRODUCTO,
            CANTIDAD,
            COSTO_COMPRA,
            FECHA_ADQUISICION,
            FECHA_EXPIRACION,
            PROVEEDOR
    );

    private static final Kardex KARDEX = new Kardex(
            1L,
            PRODUCTO_ID,
            TipoMovimiento.COMPRA,
            CANTIDAD,
            FECHA_ADQUISICION,
            COSTO_COMPRA,
            PRECIO_VENTA,
            1L,
            LOTE_ID,
            PROVEEDOR_ID,
            ""
    );

    @Mock
    LoteRepository loteRepository;
    @Mock
    ProductoRepository productoRepository;
    @Mock
    ProveedorRepository proveedorRepository;
    @Mock
    KardexRepository kardexRepository;

    @InjectMocks
    LoteService loteService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void registrarLote() {
        Mockito.when(proveedorRepository.findById(PROVEEDOR_ID)).thenReturn(Optional.of(PROVEEDOR));
        Mockito.when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.of(PRODUCTO));
        Mockito.when(loteRepository.save(any(Lote.class))).thenReturn(LOTE);

        LoteResponse actual = loteService.registrarLote(LOTE_REQUEST);

        assertEquals(LOTE_ID, actual.id());
        assertEquals(PRODUCTO_ID, actual.productoId());
        assertEquals(CANTIDAD, actual.cantidad());
        assertEquals(COSTO_COMPRA, actual.costoCompra());
        assertEquals(FECHA_ADQUISICION, actual.fechaAdquisicion());
        assertEquals(FECHA_EXPIRACION, actual.fechaExpiracion());
        assertEquals(PROVEEDOR_RESPONSE, actual.proveedor());

        verify(kardexRepository).save(any(Kardex.class));
    }

    @Test
    void registrarLoteIdProveedorNotFound() {
        Mockito.when(proveedorRepository.findById(PROVEEDOR_ID)).thenReturn(Optional.empty());

        assertThrows(IdNotFoudException.class, () -> loteService.registrarLote(LOTE_REQUEST));

        verify(proveedorRepository).findById(PROVEEDOR_ID);
        verify(productoRepository, never()).findById(PRODUCTO_ID);
    }

    @Test
    void registrarLoteIdProductoNotFound() {
        Mockito.when(proveedorRepository.findById(PROVEEDOR_ID)).thenReturn(Optional.of(PROVEEDOR));
        Mockito.when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.empty());

        assertThrows(IdNotFoudException.class, () -> loteService.registrarLote(LOTE_REQUEST));

        verify(proveedorRepository).findById(PROVEEDOR_ID);
        verify(productoRepository).findById(PRODUCTO_ID);
        verify(loteRepository, never()).save(any(Lote.class));
    }

    @Test
    void obtenerLotesPorIdProducto() {
        Mockito.when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.of(PRODUCTO));
        Mockito.when(loteRepository.encontrarLotesPorIdProductoOrdenadosPorExpiracion(PRODUCTO_ID)).thenReturn(List.of(LOTE));

        List<LoteResponse> actual = loteService.obtenerLotesPorIdProducto(PRODUCTO_ID);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(LOTE_ID, actual.getFirst().id());
    }

    @Test
    void obtenerLotesPorIdProductoNotFound() {
        Mockito.when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.empty());

        assertThrows(IdNotFoudException.class, () -> loteService.obtenerLotesPorIdProducto(PRODUCTO_ID));
        verify(productoRepository).findById(PRODUCTO_ID);
        verify(loteRepository, never()).encontrarLotesPorIdProductoOrdenadosPorExpiracion(PRODUCTO_ID);
        verify(loteRepository, never()).save(any(Lote.class));
    }

    @Test
    void actualizarStockLote() {
    }
}