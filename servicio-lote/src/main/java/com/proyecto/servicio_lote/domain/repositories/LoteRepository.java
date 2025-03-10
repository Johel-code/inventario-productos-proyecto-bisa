package com.proyecto.servicio_lote.domain.repositories;

import com.proyecto.servicio_lote.domain.models.Lote;
import com.proyecto.servicio_lote.domain.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long> {
    List<Lote> findByProducto(Producto producto);
}
