package com.proyecto.servicio_producto.domain.services.impl;

import com.proyecto.servicio_producto.app.rest.request.ProductoRequest;
import com.proyecto.servicio_producto.app.rest.response.ProductoResponse;
import com.proyecto.servicio_producto.commons.utils.GeneradorCodigoProducto;
import com.proyecto.servicio_producto.domain.models.Producto;
import com.proyecto.servicio_producto.domain.repositories.ProductoRepository;
import com.proyecto.servicio_producto.domain.services.abstract_service.IProductoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;


    @Override
    public List<ProductoResponse> mostrarTodo() {
        return productoRepository.findAll()
                .stream()
                .map(Producto::toProductoResponse)
                .toList();
    }

    @Override
    public ProductoResponse mostrarPorId(Long id) {
        var producto = productoRepository.findById(id).orElseThrow();
        return Producto.toProductoResponse(producto);
    }

    @Override
    public ProductoResponse crear(ProductoRequest request) {
        String codigo = GeneradorCodigoProducto.generateCodigo(request.nombre());
        if(productoRepository.existsByCodigoProducto(codigo)) throw new RuntimeException("El producto ya existe en la base de datos");
        var producto = Producto.builder()
                .codigoProducto(codigo)
                .nombre(request.nombre())
                .minStock(request.minStock())
                .porcentajeGanancia(request.porcentajeGanancia())
                .build();
        var saved = productoRepository.save(producto);
        return Producto.toProductoResponse(saved);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {
        var producto = productoRepository.findById(id).orElseThrow();
        producto.setMinStock(request.minStock());
        producto.setPorcentajeGanancia(request.porcentajeGanancia());
        var saved = productoRepository.save(producto);
        return Producto.toProductoResponse(saved);
    }

    @Override
    public void eliminar(Long aLong) {
        var producto = productoRepository.findById(aLong).orElseThrow();
        productoRepository.delete(producto);
    }
}
