package com.proyecto.servicio_lote.app.rest.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoteRequest(

        @NotNull(message = "El ID del proveedor no puede ser nulo")
        @Positive(message = "El ID del proveedor debe ser un número positivo")
        Long proveedorId,

        @NotNull(message = "El ID del producto no puede ser nulo")
        @Positive(message = "El ID del producto debe ser un número positivo")
        Long productoId,

        @NotNull(message = "El costo de compra no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = false, message = "El costo de compra debe ser mayor que 0")
        BigDecimal costoCompra,

        @NotNull(message = "La cantidad no puede ser nula")
        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        Integer cantidad,

        @NotNull(message = "La fecha de expiración no puede ser nula")
        @Future(message = "La fecha de expiración debe ser en el futuro")
        LocalDate fechaExpiracion
) {
}
