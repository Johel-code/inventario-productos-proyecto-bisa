package com.proyecto.servicio_reporte.domain.services;

import com.proyecto.servicio_reporte.app.rest.response.ProductoMasVendidoResponse;
import com.proyecto.servicio_reporte.app.rest.response.ProductoUmbralResponse;
import com.proyecto.servicio_reporte.domain.models.Producto;
import com.proyecto.servicio_reporte.domain.repositories.ProductoRespository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ProductoService {

    private final ProductoRespository productoRespository;

    public List<ProductoMasVendidoResponse> obtenerProductosOrdanadosPorCantidadVendida(Integer cantidadProductos, String sort){

        Sort.Order order = sort.equalsIgnoreCase("asc") ? Sort.Order.asc("cantidad") : Sort.Order.desc("cantidad");
        PageRequest pageRequest = PageRequest.of(0, cantidadProductos, Sort.by(order));

        return productoRespository.obtenerProductosOrdenadosPorCantidadVendida(pageRequest).getContent();
    }

    public List<ProductoUmbralResponse> obtenerProductosDebajoUmbral(Integer umbral) {
        if(umbral == null){
            var productos = productoRespository.findAll();
            List<ProductoUmbralResponse> productosUmbral = new ArrayList<>();
            for (Producto producto : productos) {
                if(producto.getCantidadStock() < producto.getMinStock()){
                    productosUmbral.add(Producto.aResponse(producto));
                }
            }
            return productosUmbral;
        }
        return productoRespository.findByCantidadStockLessThan(umbral);
    }
}
