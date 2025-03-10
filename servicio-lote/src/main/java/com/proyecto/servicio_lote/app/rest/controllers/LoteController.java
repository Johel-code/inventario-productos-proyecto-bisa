package com.proyecto.servicio_lote.app.rest.controllers;

import com.proyecto.servicio_lote.app.rest.request.LoteRequest;
import com.proyecto.servicio_lote.app.rest.response.LoteResponse;
import com.proyecto.servicio_lote.domain.services.LoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "lote")
public class LoteController {

    private final LoteService loteService;

    @PostMapping
    public ResponseEntity<LoteResponse> registrarLote(@RequestBody LoteRequest request){
        return ResponseEntity.ok(loteService.registrarLote(request));
    }

    @GetMapping(path = "producto/{id}")
    public ResponseEntity<List<LoteResponse>> obtenerLotesPorIdProducto(@PathVariable Long id) {
        return ResponseEntity.ok(loteService.obtenerLotesPorIdProducto(id));
    }
}
