package com.proyecto.servicio_producto.domain.models;

import com.proyecto.servicio_producto.app.rest.response.ProductoResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
  private BigDecimal costoCompra;
  private BigDecimal precioVenta;
  private Integer minStock;
  private Double porcentajeGanancia;

  @ManyToOne
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

  @OneToMany(mappedBy = "producto")
  private List<Lote> lotes;

  public int getCantidadStock() {
    int cantidadStock = 0;
    for (Lote lote : lotes) {
      cantidadStock += lote.getCantidad();
    }
    return cantidadStock;
  }

  public static BigDecimal calcularPrecioVenta(BigDecimal costoCompra, Double porcentajeGanancia) {
    return costoCompra.add(costoCompra.multiply(BigDecimal.valueOf(porcentajeGanancia + IMPUESTO_IVA + IMPUESTO_IT)).setScale(2, RoundingMode.HALF_UP));
  }

  public static ProductoResponse aResponse(Producto producto) {
    Integer cantidad = producto.lotes == null ? 0 : producto.getCantidadStock();
    return new ProductoResponse(
        producto.id,
        producto.codigoProducto,
        producto.nombre,
        producto.costoCompra,
        producto.precioVenta,
        cantidad,
        producto.minStock,
        producto.porcentajeGanancia,
        producto.getCategoria().getId()
    );
  }

  private final static Double IMPUESTO_IVA = 0.13;
  private final static Double IMPUESTO_IT  = 0.03;

}
