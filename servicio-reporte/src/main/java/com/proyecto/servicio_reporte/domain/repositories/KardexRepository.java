package com.proyecto.servicio_reporte.domain.repositories;

import com.proyecto.servicio_reporte.app.rest.response.GananciaRangoFechaResponse;
import com.proyecto.servicio_reporte.domain.models.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface KardexRepository extends JpaRepository<Kardex, Long> {

    @Query("select sum(k.precioVenta * k.cantidad - k.costoCompra * k.cantidad)" +
            " from Kardex k " +
            "where k.fechaMovimiento between :fechaIni and :fechaFin " +
            "and k.tipoMovimiento = 'VENTA'" )
    Optional<BigDecimal> obtenerGananciaRangoFecha(LocalDate fechaIni, LocalDate fechaFin);
}
