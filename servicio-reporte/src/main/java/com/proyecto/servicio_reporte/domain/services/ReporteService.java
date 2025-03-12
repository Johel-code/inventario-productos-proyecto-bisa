package com.proyecto.servicio_reporte.domain.services;

import com.proyecto.servicio_reporte.app.rest.request.GananciaRangoFechaRequest;
import com.proyecto.servicio_reporte.app.rest.response.GananciaRangoFechaResponse;
import com.proyecto.servicio_reporte.domain.repositories.KardexRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ReporteService {

    private final KardexRepository kardexRepository;

    public GananciaRangoFechaResponse obtenerGananciaRangoFecha(GananciaRangoFechaRequest request) {
        var res = kardexRepository.obtenerGananciaRangoFecha(request.fechaInicial(), request.fechaFinal());
        if(res.isPresent())
            return new GananciaRangoFechaResponse(res.get(), request.fechaInicial(), request.fechaFinal());

        return new GananciaRangoFechaResponse(BigDecimal.ZERO, request.fechaInicial(), request.fechaFinal());
    }
}
