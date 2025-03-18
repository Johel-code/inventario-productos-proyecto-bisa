package com.proyecto.servicio_venta.domain.services;

import com.proyecto.servicio_venta.app.rest.request.DetalleVentaRequest;
import com.proyecto.servicio_venta.app.rest.request.VentaRequest;
import com.proyecto.servicio_venta.app.rest.response.VentaResponse;
import com.proyecto.servicio_venta.common.exceptions.CantidadInsuficienteException;
import com.proyecto.servicio_venta.common.exceptions.IdNotFoudException;
import com.proyecto.servicio_venta.common.exceptions.PrecioNoValidoException;
import com.proyecto.servicio_venta.domain.models.*;
import com.proyecto.servicio_venta.domain.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    private static final Long PRODUCTO_ID = 1L;
    private static final Long PRODUCTO_ID_2 = 2L;
    private static final Integer CANTIDAD_A_COMPRAR = 50;
    private static final Integer CANTIDAD_A_COMPRAR_2 = 15;
    private static final BigDecimal PRECIO_VENTA = BigDecimal.TEN;
    private static final BigDecimal PRECIO_VENTA_EN_REQUEST = BigDecimal.valueOf(13);
    private static final BigDecimal COSTO_COMPRA = BigDecimal.valueOf(12);

    private static final DetalleVentaRequest DETALLE_VENTA_REQUEST = new DetalleVentaRequest(
            PRODUCTO_ID,
            CANTIDAD_A_COMPRAR,
            PRECIO_VENTA_EN_REQUEST
    );
    private static final DetalleVentaRequest DETALLE_VENTA_REQUEST_2 = DetalleVentaRequest.builder()
            .productoId(PRODUCTO_ID_2)
            .cantidad(CANTIDAD_A_COMPRAR_2)
            .build();

     private static final DetalleVentaRequest DETALLE_VENTA_PRECIO_INVALIDO_REQUEST = DetalleVentaRequest.builder()
            .productoId(PRODUCTO_ID_2)
            .cantidad(CANTIDAD_A_COMPRAR_2)
             .precioUnitario(BigDecimal.valueOf(100))
            .build();

    private static final List<DetalleVentaRequest> LIST_DETALLE_VENTA = List.of(DETALLE_VENTA_REQUEST);

    private static final VentaRequest VENTA_REQUEST = new VentaRequest(
            LIST_DETALLE_VENTA
    );

    private static final VentaRequest VENTA_REQUEST_2 = new VentaRequest(
            List.of(DETALLE_VENTA_REQUEST_2)
    );

    private static final VentaRequest VENTA_PRECIO_INVALIDO_REQUEST = new VentaRequest(
            List.of(DETALLE_VENTA_PRECIO_INVALIDO_REQUEST)
    );

    private static final Venta VENTA = Venta.builder()
            .fecha(LocalDate.now())
            .totalVenta(BigDecimal.ZERO)
            .build();

    private static final Producto PRODUCTO_1 = Producto.builder()
            .id(PRODUCTO_ID)
            .codigoProducto("PRO-9392")
            .nombre("Producto1")
            .costoCompra(COSTO_COMPRA)
            .precioVenta(PRECIO_VENTA)
            .minStock(50)
            .porcentajeGanancia(0.2)
            .build();

    private static final Producto PRODUCTO_2 = Producto.builder()
            .id(PRODUCTO_ID_2)
            .codigoProducto("PRO-2222")
            .nombre("Producto2")
            .costoCompra(COSTO_COMPRA)
            .precioVenta(PRECIO_VENTA)
            .minStock(50)
            .porcentajeGanancia(0.2)
            .build();

    private static final DetalleVenta DETALLE_VENTA_1 = new DetalleVenta(
            1L,
            CANTIDAD_A_COMPRAR,
            PRECIO_VENTA_EN_REQUEST,
            VENTA,
            PRODUCTO_ID
    );

    private static final DetalleVenta DETALLE_VENTA_2 = new DetalleVenta(
            1L,
            CANTIDAD_A_COMPRAR_2,
            PRECIO_VENTA,
            VENTA,
            PRODUCTO_ID_2
    );

    @Mock
    VentaRepository ventaRepository;
    @Mock
    ProductoRepository productoRepository;
    @Mock
    DetalleVentaRepository detalleVentaRepository;
    @Mock
    KardexRepository kardexRepository;
    @Mock
    LoteRepository loteRepository;

    @InjectMocks
    VentaService ventaService;

    @BeforeEach
    void setUp() {
        PRODUCTO_1.setLotes(List.of(Lote.builder()
                .id(1L)
                .producto(PRODUCTO_1)
                .cantidad(100)
                .build()));

        PRODUCTO_2.setLotes(List.of(Lote.builder()
                .id(2L)
                .producto(PRODUCTO_2)
                .cantidad(100)
                .costoCompra(COSTO_COMPRA)
                .build()));
    }

    @Test
    void registrarVentaConPrecioVentaTest() {
        Mockito.when(ventaRepository.save(any(Venta.class))).thenReturn(VENTA);
        Mockito.when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.of(PRODUCTO_1));
        Mockito.when(detalleVentaRepository.save(any(DetalleVenta.class))).thenReturn(DETALLE_VENTA_1);

         VentaResponse actual = ventaService.registrarVenta(VENTA_REQUEST);

         assertNotNull(actual);
         assertEquals(1, actual.detalleVenta().size());
         assertEquals(PRODUCTO_ID, actual.detalleVenta().getFirst().productoId());
         assertEquals(CANTIDAD_A_COMPRAR, actual.detalleVenta().getFirst().cantidad());
         assertEquals(PRECIO_VENTA_EN_REQUEST, actual.detalleVenta().getFirst().precioUnitario());
         assertEquals(BigDecimal.valueOf(650), actual.detalleVenta().getFirst().subTotal());
         assertEquals(BigDecimal.valueOf(650), actual.totalVenta());

         verify(kardexRepository).save(any(Kardex.class));
         verify(loteRepository).save(any(Lote.class));
    }

    @Test
    void registrarVentaSinPrecioVentaTest() {
        Mockito.when(ventaRepository.save(any(Venta.class))).thenReturn(VENTA);
        Mockito.when(productoRepository.findById(PRODUCTO_ID_2)).thenReturn(Optional.of(PRODUCTO_2));
        Mockito.when(detalleVentaRepository.save(any(DetalleVenta.class))).thenReturn(DETALLE_VENTA_2);

        VentaResponse actual = ventaService.registrarVenta(VENTA_REQUEST_2);

        assertEquals(1, actual.detalleVenta().size());
        assertEquals(PRODUCTO_ID_2, actual.detalleVenta().getFirst().productoId());
        assertEquals(PRECIO_VENTA, actual.detalleVenta().getFirst().precioUnitario());

        verify(kardexRepository).save(any(Kardex.class));
        verify(loteRepository).save(any(Lote.class));
    }

    @Test
    void registrarVentaIdProductoNotFoundTest() {
        Mockito.when(productoRepository.findById(PRODUCTO_ID_2)).thenReturn(Optional.empty());

        assertThrows(IdNotFoudException.class, () -> ventaService.registrarVenta(VENTA_REQUEST_2));

        verify(ventaRepository).save(any(Venta.class));
        verify(detalleVentaRepository, never()).save(any(DetalleVenta.class));
    }

    @Test
    void registrarVentaPrecioVentaInvalidoTest() {
        Mockito.when(ventaRepository.save(any(Venta.class))).thenReturn(VENTA);
        Mockito.when(productoRepository.findById(PRODUCTO_ID_2)).thenReturn(Optional.of(PRODUCTO_2));

        assertThrows(PrecioNoValidoException.class, () -> ventaService.registrarVenta(VENTA_PRECIO_INVALIDO_REQUEST));

        verify(detalleVentaRepository, never()).save(any(DetalleVenta.class));
    }

    @Test
    void registarVentaCantidadInsuficienteTest() {
        Mockito.when(ventaRepository.save(any(Venta.class))).thenReturn(VENTA);
        Mockito.when(productoRepository.findById(PRODUCTO_ID_2)).thenReturn(Optional.of(PRODUCTO_2));
        Mockito.when(detalleVentaRepository.save(any(DetalleVenta.class))).thenReturn(DETALLE_VENTA_2);

        PRODUCTO_2.getLotes().getFirst().setCantidad(10);

        assertThrows(CantidadInsuficienteException.class, () -> ventaService.registrarVenta(VENTA_REQUEST_2));

        verify(kardexRepository).save(any(Kardex.class));
        verify(loteRepository).save(any(Lote.class));
    }
}