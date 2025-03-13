package com.proyecto.servicio_lote.domain.repositories;

import com.proyecto.servicio_lote.domain.models.Lote;
import com.proyecto.servicio_lote.domain.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long> {

    @Query("select l from Lote l join fetch Producto p " +
            "on l.producto = p " +
            "where p.id = :productoId " +
            "order by l.fechaExpiracion asc")
    List<Lote> encontrarLotesPorIdProductoOrdenadosPorExpiracion(Long productoId);
}
