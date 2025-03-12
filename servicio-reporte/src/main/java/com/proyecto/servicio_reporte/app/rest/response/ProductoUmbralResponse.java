package com.proyecto.servicio_reporte.app.rest.response;

import java.math.BigDecimal;

public record ProductoUmbralResponse(
        Long id,
        String codigoProducto,
        String nombre,
        BigDecimal costoCompra,
        BigDecimal precioVenta,
        Integer cantidadStock,
        Integer minStock,
        Long categoriaId
) {
}
