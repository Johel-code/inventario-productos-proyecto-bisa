package com.proyecto.servicio_venta.clients;

import com.proyecto.servicio_venta.app.rest.request.LoteCantidadRequest;
import com.proyecto.servicio_venta.domain.models.Lote;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(url = "localhost:8083/lote", name = "servicio-lote")
public interface LoteFeignClient {

    @GetMapping(path = "producto-vencimiento/{id}")
    List<Lote> obtenerLotesOrdenadosPorVencimiento(@PathVariable Long id);

    @PutMapping(path = "actualizar-stock/{id}")
    void actualizarStock(@PathVariable Long id, @RequestBody LoteCantidadRequest cantidad);
}