package com.proyecto.servicio_venta.app.rest.response;

import java.math.BigDecimal;

public record ProductoDTO(
        Long id,
        String codigoProducto,
        String nombre,
        Integer minStock,
        BigDecimal precioUnitario
) {
}
