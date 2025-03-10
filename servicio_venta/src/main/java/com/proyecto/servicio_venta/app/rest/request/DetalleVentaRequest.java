package com.proyecto.servicio_venta.app.rest.request;

import java.math.BigDecimal;

public record DetalleVentaRequest(
        Long productoId,
        Integer cantidad,
        BigDecimal precioUnitario
) {
}
