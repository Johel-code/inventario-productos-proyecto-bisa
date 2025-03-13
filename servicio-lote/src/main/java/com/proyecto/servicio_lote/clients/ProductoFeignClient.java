package com.proyecto.servicio_lote.clients;

import com.proyecto.servicio_lote.app.rest.request.ProductoCantidadRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(url = "localhost:8081/producto", name = "servicio-producto")
public interface ProductoFeignClient {

    @PutMapping(path = "actualizar-stock/{id}")
    void actualizarStock(@PathVariable Long id, @RequestBody ProductoCantidadRequest cantidad);

    @PutMapping(path = "actualizar-costo-compra/{id}")
    void actualizarCostoCompra(@PathVariable Long id);
}