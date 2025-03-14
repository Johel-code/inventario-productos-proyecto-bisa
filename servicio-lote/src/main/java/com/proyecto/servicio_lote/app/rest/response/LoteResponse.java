package com.proyecto.servicio_lote.app.rest.response;

import com.proyecto.servicio_lote.domain.models.Producto;
import com.proyecto.servicio_lote.domain.models.Proveedor;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoteResponse (
        Long id,
        Long productoId,
        Integer cantidad,
        BigDecimal costoCompra,
        LocalDate fechaAdquisicion,
        LocalDate fechaExpiracion,
        ProveedorResponse proveedor
){
}
