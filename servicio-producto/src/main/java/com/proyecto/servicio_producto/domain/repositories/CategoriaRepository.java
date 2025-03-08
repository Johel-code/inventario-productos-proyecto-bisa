package com.proyecto.servicio_producto.domain.repositories;

import com.proyecto.servicio_producto.domain.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
