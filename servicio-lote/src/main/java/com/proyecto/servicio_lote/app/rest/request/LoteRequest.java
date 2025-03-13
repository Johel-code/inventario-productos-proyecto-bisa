package com.proyecto.servicio_lote.app.rest.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoteRequest(
        Long proveedorId,
        Long productoId,
        BigDecimal costoCompra,
        Integer cantidad,
        LocalDate fechaExpiracion
) {
}
