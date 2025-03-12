package com.proyecto.servicio_reporte.app.rest.controllers;

import com.proyecto.servicio_reporte.app.rest.response.ProductoMasVendidoResponse;
import com.proyecto.servicio_reporte.domain.services.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "reporte")
@AllArgsConstructor
public class ProductoController {

    private ProductoService productoService;

    @GetMapping(path = "mas-vendidos")
    public ResponseEntity<List<ProductoMasVendidoResponse>> obtenerNProductosMasVendidos(@RequestParam Integer cantidad) {
        return ResponseEntity.ok(productoService.obtenerProductosOrdanadosPorCantidadVendida(cantidad, "desc"));
    }

    @GetMapping(path = "menos-vendidos")
    public ResponseEntity<List<ProductoMasVendidoResponse>> obtenerNProductosMenosVendidos(@RequestParam Integer cantidad) {
        return ResponseEntity.ok(productoService.obtenerProductosOrdanadosPorCantidadVendida(cantidad, "asc"));
    }
}
