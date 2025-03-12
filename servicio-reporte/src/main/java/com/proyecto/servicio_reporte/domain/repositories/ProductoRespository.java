package com.proyecto.servicio_reporte.domain.repositories;

import com.proyecto.servicio_reporte.app.rest.response.ProductoMasVendidoResponse;
import com.proyecto.servicio_reporte.app.rest.response.ProductoUmbralResponse;
import com.proyecto.servicio_reporte.domain.models.Producto;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRespository extends JpaRepository<Producto, Long> {

    @Query("SELECT new com.proyecto.servicio_reporte.app.rest.response.ProductoMasVendidoResponse( " +
            "p.id, p.codigoProducto, p.nombre, p.categoriaId, CAST(SUM(d.cantidad) as INTEGER) ) " +
            "FROM DetalleVenta d JOIN d.producto p " +
            "GROUP BY p.id, p.codigoProducto, p.nombre, p.categoriaId, d.cantidad ")
    Page<ProductoMasVendidoResponse> obtenerProductosOrdenadosPorCantidadVendida(Pageable pageable);

    List<ProductoUmbralResponse> findByCantidadStockLessThan(Integer umbral);

}
