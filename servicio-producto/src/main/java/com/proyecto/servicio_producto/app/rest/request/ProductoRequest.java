package com.proyecto.servicio_producto.app.rest.request;

import java.math.BigDecimal;

public record ProductoRequest(
        String nombre,
        BigDecimal costoCompra,
        BigDecimal precioVenta,
        Integer minStock,
        //Double porcentajeGanancia,
        Long categoriaId
) {
}
