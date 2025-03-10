package com.proyecto.servicio_venta.domain.repositories;

import com.proyecto.servicio_venta.domain.models.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KardexRepository extends JpaRepository<Kardex, Long> {
}
