package com.proyecto.servicio_venta.app.rest.request;

import java.math.BigDecimal;
import java.util.List;

public record VentaRequest(
        BigDecimal totalVenta,
        List<DetalleVentaRequest> detalleVenta
) {
}
