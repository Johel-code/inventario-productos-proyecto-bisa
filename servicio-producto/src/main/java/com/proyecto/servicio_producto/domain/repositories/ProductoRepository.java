package com.proyecto.servicio_producto.domain.repositories;

import com.proyecto.servicio_producto.domain.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByCodigoProducto(String codigoProducto);

}
