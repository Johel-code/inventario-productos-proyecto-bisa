package com.proyecto.servicio_producto.domain.services.impl;

import com.proyecto.servicio_producto.app.rest.request.ProductoCantidadRequest;
import com.proyecto.servicio_producto.app.rest.request.ProductoRequest;
import com.proyecto.servicio_producto.app.rest.response.ProductoResponse;
import com.proyecto.servicio_producto.commons.utils.GeneradorCodigoProducto;
import com.proyecto.servicio_producto.domain.models.Lote;
import com.proyecto.servicio_producto.domain.models.Producto;
import com.proyecto.servicio_producto.domain.repositories.CategoriaRepository;
import com.proyecto.servicio_producto.domain.repositories.ProductoRepository;
import com.proyecto.servicio_producto.domain.services.abstract_service.IProductoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;


    @Override
    public List<ProductoResponse> mostrarTodo() {
        return productoRepository.findAll()
                .stream()
                .map(Producto::aResponse)
                .toList();
    }

    @Override
    public ProductoResponse mostrarPorId(Long id) {
        var producto = productoRepository.findById(id).orElseThrow();
        return Producto.aResponse(producto);
    }

    @Override
    public ProductoResponse crear(ProductoRequest request) {
        var categoria = categoriaRepository.findById(request.categoriaId()).orElseThrow();

        String codigo = GeneradorCodigoProducto.generateCodigo(request.nombre());
        if(productoRepository.existsByCodigoProducto(codigo)) throw new RuntimeException("El producto ya existe en la base de datos");

        var producto = Producto.builder()
                .codigoProducto(codigo)
                .nombre(request.nombre())
                .costoCompra(request.costoCompra())
                .precioVenta(calcularPrecioVenta(request.costoCompra(), request.porcentajeGanancia()))
                .cantidadStock(0)
                .minStock(request.minStock())
                .porcentajeGanancia(request.porcentajeGanancia())
                .categoria(categoria)
                .build();
        var saved = productoRepository.save(producto);
        return Producto.aResponse(saved);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {
        var producto = productoRepository.findById(id).orElseThrow();
        if(request.nombre()!=null) {
            producto.setNombre(request.nombre());
            String codigo = GeneradorCodigoProducto.generateCodigo(request.nombre());
            if(productoRepository.existsByCodigoProducto(codigo)) throw new RuntimeException("El producto ya existe en la base de datos");   producto.setNombre(request.nombre());
            producto.setCodigoProducto(codigo);
        }
        producto.setCostoCompra(request.costoCompra());
        producto.setPorcentajeGanancia(request.porcentajeGanancia());
        producto.setPrecioVenta(calcularPrecioVenta(request.costoCompra(),
                request.porcentajeGanancia() != null ? request.porcentajeGanancia() : producto.getPorcentajeGanancia()));
        producto.setMinStock(request.minStock());
        if(request.categoriaId()!=null) {
            producto.setCategoria(categoriaRepository.findById(request.categoriaId()).orElseThrow());
        }
        var saved = productoRepository.save(producto);
        return Producto.aResponse(saved);
    }

    private BigDecimal calcularPrecioVenta(BigDecimal costoCompra, Double porcentajeGanancia) {
        return costoCompra.add(costoCompra.multiply(BigDecimal.valueOf(porcentajeGanancia + IMPUESTO_IVA + IMPUESTO_IT)));
    }

    @Override
    public void eliminar(Long aLong) {
        var producto = productoRepository.findById(aLong).orElseThrow();
        productoRepository.delete(producto);
    }

    public void actualizarStock(Long id, ProductoCantidadRequest request) {
        var producto = productoRepository.findById(id).orElseThrow();
        producto.setCantidadStock(request.cantidadStock());
        productoRepository.save(producto);
    }

    public void actualizarCostoCompra(Long id) {
        var producto = productoRepository.findById(id).orElseThrow();
        var lotes = producto.getLotes();

        BigDecimal sumaCostosPonderados = BigDecimal.ZERO;
        int sumaCantidades = 0;

        for(Lote lote : lotes) {
            sumaCostosPonderados = sumaCostosPonderados.add(
                    lote.getCostoCompra().multiply(BigDecimal.valueOf(lote.getCantidad()))
            );
            sumaCantidades += lote.getCantidad();
        }

        BigDecimal nuevoCostoCompra = sumaCantidades == 0 ? BigDecimal.ZERO :
                sumaCostosPonderados.divide(BigDecimal.valueOf(sumaCantidades), 2, RoundingMode.HALF_UP);

        producto.setCostoCompra(nuevoCostoCompra);
        producto.setPrecioVenta(calcularPrecioVenta(nuevoCostoCompra, producto.getPorcentajeGanancia()));
        productoRepository.save(producto);
    }

    private final static Double IMPUESTO_IVA = 0.13;
    private final static Double IMPUESTO_IT  = 0.03;
}
