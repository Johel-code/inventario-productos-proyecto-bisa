package com.proyecto.servicio_venta.app.rest.response;

import com.proyecto.servicio_venta.app.rest.request.DetalleVentaRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record VentaResponse(
        Long id,
        LocalDate fecha,
        BigDecimal totalVenta,
        List<DetalleVentaResponse> detalleVenta
) {
}
