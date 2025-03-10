package com.proyecto.servicio_lote.domain.repositories;

import com.proyecto.servicio_lote.domain.models.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteRepository extends JpaRepository<Lote, Integer> {
}
