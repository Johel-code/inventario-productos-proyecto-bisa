package com.proyecto.servicio_venta.domain.repositories;

import com.proyecto.servicio_venta.domain.models.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
}
