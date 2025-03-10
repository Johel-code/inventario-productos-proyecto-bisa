package com.proyecto.servicio_producto.domain.models;

import com.proyecto.servicio_producto.app.rest.response.ProductoResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
  private Integer minStock;
  private Double porcentajeGanancia;

  @ManyToOne
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

  public static ProductoResponse toProductoResponse(Producto producto) {
    return new ProductoResponse(
        producto.id,
        producto.codigoProducto,
        producto.nombre,
        producto.minStock,
        producto.porcentajeGanancia,
        producto.getCategoria().getId()
    );
  }

}
