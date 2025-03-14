package com.proyecto.servicio_lote.app.rest.response;

import java.math.BigDecimal;

public record ProductoResponse(
        Long id,
        String codigoProducto,
        String nombre,
        BigDecimal costoCompra,
        BigDecimal precioVenta,
        Integer minStock,
        Double porcentajeGanancia
) {
}
