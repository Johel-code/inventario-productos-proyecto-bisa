package com.proyecto.servicio_producto.app.rest.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductoRequest(

        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotNull(message = "El costo de compra no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = false, message = "El costo de compra debe ser mayor que 0")
        BigDecimal costoCompra,

        @NotNull(message = "El stock mínimo no puede ser nulo")
        @Min(value = 0, message = "El stock mínimo no puede ser menor que 0")
        Integer minStock,

        @NotNull(message = "El porcentaje de ganancia no puede ser nulo")
        @DecimalMin(value = "0.0", inclusive = true, message = "El porcentaje de ganancia no puede ser menor que 0")
        @DecimalMax(value = "1.0", inclusive = true, message = "El porcentaje de ganancia no puede ser mayor que 1")
        Double porcentajeGanancia,

        @NotNull(message = "El ID de la categoría no puede ser nulo")
        @Positive(message = "El ID de la categoría debe ser un número positivo")
        Long categoriaId
) {
}
