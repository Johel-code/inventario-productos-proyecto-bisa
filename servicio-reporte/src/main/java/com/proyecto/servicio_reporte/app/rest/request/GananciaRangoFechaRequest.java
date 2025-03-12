package com.proyecto.servicio_reporte.app.rest.request;

import java.time.LocalDate;

public record GananciaRangoFechaRequest(
        LocalDate fechaInicial,
        LocalDate fechaFinal
) {
}
