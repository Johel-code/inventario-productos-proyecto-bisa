package com.proyecto.servicio_reporte.app.rest.response;

public record ProductoMasVendidoResponse(
        Long id,
        String codigoProducto,
        String nombre,
        Long categoriaId,
        Integer cantidadVendida
) {
}
