package com.proyecto.servicio_venta.app.rest.response;

import java.math.BigDecimal;

public record DetalleVentaResponse(
        Long productoId,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subTotal
) {
}
