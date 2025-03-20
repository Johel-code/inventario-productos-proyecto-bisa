package com.proyecto.servicio_venta.clients;

import com.proyecto.servicio_venta.app.rest.request.ProductoCantidadRequest;
import com.proyecto.servicio_venta.domain.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(url = "localhost:8081/producto", name = "servicio-producto")
public interface ProductoFeignClient {

    @GetMapping(path = "{id}")
    Producto mostrarProductoPorId(@PathVariable Long id);

    @PutMapping(path = "actualizar-stock/{id}")
    void actualizarStock(@PathVariable Long id, @RequestBody ProductoCantidadRequest cantidad);

    @PutMapping(path = "actualizar-costo-compra/{id}")
    void actualizarCostoCompra(@PathVariable Long id);

}