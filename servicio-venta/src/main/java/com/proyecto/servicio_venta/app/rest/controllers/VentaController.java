package com.proyecto.servicio_venta.app.rest.controllers;

import com.proyecto.servicio_venta.app.rest.request.VentaRequest;
import com.proyecto.servicio_venta.app.rest.response.VentaResponse;
import com.proyecto.servicio_venta.domain.services.VentaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping(path = "venta")
@RestController
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponse> realizarVenta(@Valid @RequestBody VentaRequest ventaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrarVenta(ventaRequest));
    }
}
