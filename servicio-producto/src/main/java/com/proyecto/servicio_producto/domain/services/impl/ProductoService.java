package com.proyecto.servicio_producto.domain.services.impl;

import com.proyecto.servicio_producto.app.rest.request.ProductoCantidadRequest;
import com.proyecto.servicio_producto.app.rest.request.ProductoRequest;
import com.proyecto.servicio_producto.app.rest.response.ProductoResponse;
import com.proyecto.servicio_producto.commons.utils.GeneradorCodigoProducto;
import com.proyecto.servicio_producto.domain.models.Producto;
import com.proyecto.servicio_producto.domain.repositories.CategoriaRepository;
import com.proyecto.servicio_producto.domain.repositories.ProductoRepository;
import com.proyecto.servicio_producto.domain.services.abstract_service.IProductoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
                .precioVenta(request.precioVenta())
                .cantidadStock(0)
                .minStock(request.minStock())
                //.porcentajeGanancia(request.porcentajeGanancia())
                .categoria(categoria)
                .build();
        var saved = productoRepository.save(producto);
        return Producto.aResponse(saved);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {
        var producto = productoRepository.findById(id).orElseThrow();
        producto.setCostoCompra(request.costoCompra());
        producto.setPrecioVenta(request.precioVenta());
        producto.setMinStock(request.minStock());
        //producto.setPorcentajeGanancia(request.porcentajeGanancia());
        var saved = productoRepository.save(producto);
        return Producto.aResponse(saved);
    }

    private BigDecimal calcularPrecioVenta(BigDecimal costoCompra, Double porcentajeGanancia) {
        return costoCompra.add(costoCompra.multiply(BigDecimal.valueOf(porcentajeGanancia)));
    }

    @Override
    public void eliminar(Long aLong) {
        var producto = productoRepository.findById(aLong).orElseThrow();
        productoRepository.delete(producto);
    }

//    public ProductoResponse actualizarStock(ProductoCantidadRequest request, Long id) {
//        var producto = productoRepository.findById(id).orElseThrow();
//        producto.setCantidadStock(request.cantidadStock());
//        var saved = productoRepository.save(producto);
//        return Producto.toProductoResponse(saved);
//    }

    public void validarPrecio(BigDecimal costoCompra, BigDecimal precioVenta) {
        BigDecimal precioMinimo = costoCompra.multiply(new BigDecimal("0.75"));
        BigDecimal precioMaximo = costoCompra.add(precioMinimo);
        if (precioVenta.compareTo(precioMinimo) < 0 || precioVenta.compareTo(precioMaximo) > 0) {
            throw new RuntimeException("El precio de venta no es valido");
        }
    }

    public void actualizarStock(Long id, ProductoCantidadRequest request) {
        var producto = productoRepository.findById(id).orElseThrow();
        producto.setCantidadStock(request.cantidadStock());
        productoRepository.save(producto);
    }
}
