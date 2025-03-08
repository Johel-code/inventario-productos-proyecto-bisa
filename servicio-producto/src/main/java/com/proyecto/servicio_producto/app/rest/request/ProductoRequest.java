package com.proyecto.servicio_producto.app.rest.request;

public record ProductoRequest(
        String nombre,
        Integer minStock,
        Double porcentajeGanancia,
        Long categoria_id
) {
}
