package com.proyecto.servicio_reporte.app.rest.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GananciaRangoFechaResponse(
        BigDecimal ganancia,
        LocalDate fechaInicial,
        LocalDate fechaFinal
) {
}
