package com.proyecto.servicio_producto.app.rest.response;

public record ProductoResponse(
        Long id,
        String codigoProducto,
        String nombre,
        Integer minStock,
        Double porcentajeGanancia,
        Long categoriaId
) {
}
