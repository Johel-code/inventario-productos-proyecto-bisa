package com.proyecto.servicio_venta.domain.repositories;

import com.proyecto.servicio_venta.domain.models.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteRepository extends JpaRepository<Lote, Long> {
}
