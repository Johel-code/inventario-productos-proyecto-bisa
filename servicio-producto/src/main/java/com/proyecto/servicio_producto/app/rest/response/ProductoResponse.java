package com.proyecto.servicio_producto.app.rest.response;

import java.math.BigDecimal;

public record ProductoResponse(
        Long id,
        String codigoProducto,
        String nombre,
        BigDecimal costoCompra,
        BigDecimal precioVenta,
        Integer cantidadStock,
        Integer minStock,
        Double porcentajeGanancia,
        Long categoriaId
) {
}
