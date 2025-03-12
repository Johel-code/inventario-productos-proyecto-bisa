package com.proyecto.servicio_reporte.domain.services;

import com.proyecto.servicio_reporte.app.rest.response.ProductoMasVendidoResponse;
import com.proyecto.servicio_reporte.domain.repositories.ProductoRespository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ProductoService {

    private final ProductoRespository productoRespository;

    public List<ProductoMasVendidoResponse> obtenerProductosOrdanadosPorCantidadVendida(Integer cantidadProductos, String sort){

        Sort.Order order = sort.equalsIgnoreCase("asc") ? Sort.Order.asc("cantidad") : Sort.Order.desc("cantidad");
        PageRequest pageRequest = PageRequest.of(0, cantidadProductos, Sort.by(order));

        return productoRespository.obtenerProductosOrdenadosPorCantidadVendida(pageRequest).getContent();
    }
}
