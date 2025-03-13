package com.proyecto.servicio_producto.domain.models;

import com.proyecto.servicio_producto.app.rest.response.ProductoResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String codigoProducto;
  private String nombre;
  private BigDecimal costoCompra;
  private BigDecimal precioVenta;
  private Integer cantidadStock;
  private Integer minStock;
  private Double porcentajeGanancia;

  @ManyToOne
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

  public static ProductoResponse aResponse(Producto producto) {
    return new ProductoResponse(
        producto.id,
        producto.codigoProducto,
        producto.nombre,
        producto.costoCompra,
        producto.precioVenta,
        producto.cantidadStock,
        producto.minStock,
        producto.getCategoria().getId()
    );
  }

}
