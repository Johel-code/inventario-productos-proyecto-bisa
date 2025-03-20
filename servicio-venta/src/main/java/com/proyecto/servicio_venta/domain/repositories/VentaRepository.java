package com.proyecto.servicio_venta.domain.repositories;

import com.proyecto.servicio_venta.domain.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}
