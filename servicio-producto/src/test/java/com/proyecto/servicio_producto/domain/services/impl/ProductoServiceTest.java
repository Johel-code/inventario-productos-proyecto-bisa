package com.proyecto.servicio_producto.domain.services.impl;

import com.proyecto.servicio_producto.app.rest.request.ProductoRequest;
import com.proyecto.servicio_producto.app.rest.response.ProductoResponse;
import com.proyecto.servicio_producto.commons.exceptions.CodigoProductoExisteExcepcion;
import com.proyecto.servicio_producto.commons.exceptions.IdNotFoudException;
import com.proyecto.servicio_producto.commons.utils.GeneradorCodigoProducto;
import com.proyecto.servicio_producto.domain.models.Categoria;
import com.proyecto.servicio_producto.domain.models.Lote;
import com.proyecto.servicio_producto.domain.models.Producto;
import com.proyecto.servicio_producto.domain.repositories.CategoriaRepository;
import com.proyecto.servicio_producto.domain.repositories.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    private static final Long PRODUCTO_ID = 1L;
    private static final String CODIGO_PRODUCTO = "TNM-3921";
    private static final String NOMBRE = "Teclado numerico mecanico";
    private static final BigDecimal COSTO_COMPRA = BigDecimal.valueOf(10);
    private static final BigDecimal PRECIO_VENTA = BigDecimal.valueOf(12);
    private static final Integer CANTIDAD_STOCK = 0;
    private static final Integer MIN_STOCK = 50;
    private static final Double PORCENTAJE_GANANCIA = 0.2;
    private static final Categoria CATEGORIA = Categoria.builder().id(1L).build();
    private static final List<Lote> LOTES = new ArrayList<>();
    private static final Long CATEGORIA_ID = 1L;

    private static final Producto PRODUCTO = new Producto(
            PRODUCTO_ID,
            CODIGO_PRODUCTO,
            NOMBRE,
            COSTO_COMPRA,
            PRECIO_VENTA,
            MIN_STOCK,
            PORCENTAJE_GANANCIA,
            CATEGORIA,
            LOTES
    );

    private static final ProductoRequest PRODUCTO_REQUEST = new ProductoRequest(
            NOMBRE,
            COSTO_COMPRA,
            MIN_STOCK,
            PORCENTAJE_GANANCIA,
            CATEGORIA_ID
    );

    private static final Optional<Producto> PRODUCTO_OPTIONAL = Optional.of(PRODUCTO);
    private static final List<Producto> PRODUCTOS = List.of(PRODUCTO);

    @Mock
    ProductoRepository productoRepository;
    @Mock
    CategoriaRepository categoriaRepository;

    @InjectMocks
    ProductoService productoService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void mostrarTodoTest() {
        Mockito.when(productoRepository.findAll()).thenReturn(PRODUCTOS);

        List<ProductoResponse> actual = productoService.mostrarTodo();
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertFalse(actual.isEmpty());
        assertEquals(PRODUCTOS.getFirst().getId(), actual.getFirst().id());
    }

    @Test
    void mostrarPorIdTest() {
        Mockito.when(productoRepository.findById(PRODUCTO_ID)).thenReturn(PRODUCTO_OPTIONAL);

        ProductoResponse actual = productoService.mostrarPorId(PRODUCTO_ID);
        assertNotNull(actual);
        assertEquals(PRODUCTO_ID, actual.id());
        assertEquals(CODIGO_PRODUCTO, actual.codigoProducto());
        assertEquals(NOMBRE, actual.nombre());
        assertEquals(COSTO_COMPRA, actual.costoCompra());
        assertEquals(PRECIO_VENTA, actual.precioVenta());
        assertEquals(CANTIDAD_STOCK, actual.cantidadStock() );
        assertEquals(MIN_STOCK, actual.minStock());
        assertEquals(PORCENTAJE_GANANCIA, actual.porcentajeGanancia());
        assertEquals(CATEGORIA.getId(), actual.categoriaId());
    }

    @Test
    void mostrarPorIdNotFoundTest() {
        Mockito.when(productoRepository.findById(PRODUCTO_ID)).thenThrow(new IdNotFoudException("Producto"));

        assertThrows(IdNotFoudException.class, () -> productoService.mostrarPorId(PRODUCTO_ID));
    }

    @Test
    void crearTest() {
        Mockito.when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(Optional.of(CATEGORIA));
        Mockito.when(productoRepository.existsByCodigoProducto(anyString())).thenReturn(Boolean.FALSE);
        Mockito.when(productoRepository.save(any(Producto.class))).thenReturn(PRODUCTO);

        ProductoResponse actual = productoService.crear(PRODUCTO_REQUEST);

        assertNotNull(actual);
        assertEquals(PRODUCTO_ID, actual.id());
        assertEquals(CODIGO_PRODUCTO, actual.codigoProducto());
        assertEquals(NOMBRE, actual.nombre());
        assertEquals(COSTO_COMPRA, actual.costoCompra());
        assertEquals(PRECIO_VENTA, actual.precioVenta());
        assertEquals(CANTIDAD_STOCK, actual.cantidadStock() );
        assertEquals(MIN_STOCK, actual.minStock());
        assertEquals(PORCENTAJE_GANANCIA, actual.porcentajeGanancia());
        assertEquals(CATEGORIA.getId(), actual.categoriaId());
    }

    @Test
    void crearCodigoProductoExistenteTest() {
        Mockito.when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(Optional.of(CATEGORIA));

        try (MockedStatic<GeneradorCodigoProducto> mocked = Mockito.mockStatic(GeneradorCodigoProducto.class)) {
            mocked.when(() -> GeneradorCodigoProducto.generateCodigo(NOMBRE)).thenReturn(CODIGO_PRODUCTO);

            Mockito.when(productoRepository.existsByCodigoProducto(CODIGO_PRODUCTO)).thenReturn(true);

            assertThrows(CodigoProductoExisteExcepcion.class, () -> {
                productoService.crear(PRODUCTO_REQUEST);
            });
        }
    }

    @Test
    void actualizar() {
    }

    @Test
    void eliminar() {
    }

    @Test
    void actualizarCostoCompra() {
    }
}