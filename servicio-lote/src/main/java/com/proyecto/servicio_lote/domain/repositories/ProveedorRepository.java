package com.proyecto.servicio_lote.domain.repositories;

import com.proyecto.servicio_lote.domain.models.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
}
