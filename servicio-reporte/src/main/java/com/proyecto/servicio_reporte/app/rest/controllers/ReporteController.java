package com.proyecto.servicio_reporte.app.rest.controllers;

import com.proyecto.servicio_reporte.app.rest.request.GananciaRangoFechaRequest;
import com.proyecto.servicio_reporte.app.rest.response.GananciaRangoFechaResponse;
import com.proyecto.servicio_reporte.app.rest.response.ProductoMasVendidoResponse;
import com.proyecto.servicio_reporte.app.rest.response.ProductoUmbralResponse;
import com.proyecto.servicio_reporte.domain.services.ProductoService;
import com.proyecto.servicio_reporte.domain.services.ReporteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "reporte")
@AllArgsConstructor
public class ReporteController {

    private ProductoService productoService;
    private ReporteService reporteService;

    @GetMapping(path = "mas-vendidos")
    public ResponseEntity<List<ProductoMasVendidoResponse>> obtenerNProductosMasVendidos(@RequestParam Integer cantidad) {
        return ResponseEntity.ok(productoService.obtenerProductosOrdanadosPorCantidadVendida(cantidad, "desc"));
    }

    @GetMapping(path = "menos-vendidos")
    public ResponseEntity<List<ProductoMasVendidoResponse>> obtenerNProductosMenosVendidos(@RequestParam Integer cantidad) {
        return ResponseEntity.ok(productoService.obtenerProductosOrdanadosPorCantidadVendida(cantidad, "asc"));
    }

    @GetMapping(path = "debajo-umbral")
    public ResponseEntity<List<ProductoUmbralResponse>> obtenerProductoDebajoUmbral(@RequestParam(required = false) Integer umbral) {
        return ResponseEntity.ok(productoService.obtenerProductosDebajoUmbral(umbral));
    }

    @PostMapping(path = "ganancia-rango-fechas")
    public ResponseEntity<GananciaRangoFechaResponse> obtenerGananciaRangoFecha(@RequestBody GananciaRangoFechaRequest request) {
        return ResponseEntity.ok(reporteService.obtenerGananciaRangoFecha(request));
    }
}
