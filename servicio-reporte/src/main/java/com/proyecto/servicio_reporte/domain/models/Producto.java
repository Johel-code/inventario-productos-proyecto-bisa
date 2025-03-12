package com.proyecto.servicio_reporte.domain.models;

import com.proyecto.servicio_reporte.app.rest.response.ProductoMasVendidoResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Producto {

    @Id
    private Long id;
    private String codigoProducto;
    private String nombre;
    private BigDecimal costoCompra;
    private BigDecimal precioVenta;
    private Integer cantidadStock;
    private Integer minStock;

    private Long categoriaId;

    @OneToMany(mappedBy = "producto")
    private List<DetalleVenta> detalleVentas;

}
