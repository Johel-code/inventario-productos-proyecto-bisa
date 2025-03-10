package com.proyecto.servicio_lote.domain.repositories;

import com.proyecto.servicio_lote.domain.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
