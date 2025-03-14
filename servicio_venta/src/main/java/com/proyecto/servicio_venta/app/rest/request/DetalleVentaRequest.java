package com.proyecto.servicio_venta.app.rest.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DetalleVentaRequest(

        @NotNull(message = "El ID del producto no puede ser nulo")
        @Positive(message = "El ID del producto debe ser un número positivo")
        Long productoId,

        @NotNull(message = "La cantidad no puede ser nula")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        Integer cantidad,

        @DecimalMin(value = "0.0", inclusive = false, message = "El precio unitario debe ser mayor que 0")
        BigDecimal precioUnitario
) {
}
