package com.proyecto.servicio_lote.app.rest.controllers;

import com.proyecto.servicio_lote.app.rest.request.LoteCantidadRequest;
import com.proyecto.servicio_lote.app.rest.request.LoteRequest;
import com.proyecto.servicio_lote.app.rest.response.LoteResponse;
import com.proyecto.servicio_lote.domain.services.LoteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "lote")
public class LoteController {

    private final LoteService loteService;

    @PostMapping
    public ResponseEntity<LoteResponse> registrarLote(@Valid @RequestBody LoteRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(loteService.registrarLote(request));
    }

    @GetMapping(path = "producto-vencimiento/{id}")
    public ResponseEntity<List<LoteResponse>> obtenerLotesPorIdProductoOrdenadosPorVencimiento(@PathVariable Long id) {
        return ResponseEntity.ok(loteService.obtenerLotesPorIdProducto(id));
    }

    @PutMapping(path = "actualizar-stock/{id}")
    public ResponseEntity<Void> actualizarStock(@PathVariable Long id, @RequestBody LoteCantidadRequest request) {
        loteService.actualizarStockLote(id, request);
        return ResponseEntity.ok().build();
    }
}
