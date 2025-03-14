package com.proyecto.servicio_venta.app.rest.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record VentaRequest(

        @NotNull(message = "La lista de detalles de venta no puede ser nula")
        @Size(min = 1, message = "Debe haber al menos un detalle de venta")
        List<@Valid DetalleVentaRequest> detalleVenta
) {
}
