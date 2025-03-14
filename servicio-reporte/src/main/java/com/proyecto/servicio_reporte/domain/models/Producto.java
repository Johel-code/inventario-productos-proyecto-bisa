package com.proyecto.servicio_reporte.domain.models;

import com.proyecto.servicio_reporte.app.rest.response.ProductoMasVendidoResponse;
import com.proyecto.servicio_reporte.app.rest.response.ProductoUmbralResponse;
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
    private Integer minStock;

    private Long categoriaId;

    @OneToMany(mappedBy = "producto")
    private List<Lote> lotes;

    @OneToMany(mappedBy = "producto")
    private List<DetalleVenta> detalleVentas;

    @OneToMany(mappedBy = "producto")
    private List<Kardex> kardexes;

    public int getCantidadStock() {
        int cantidadStock = 0;
        for (Lote lote : lotes) {
            cantidadStock += lote.getCantidad();
        }
        return cantidadStock;
    }

    public static ProductoUmbralResponse aResponse(Producto producto) {
        int cantidad = producto.getLotes() == null ? 0 : producto.getCantidadStock();
        return new ProductoUmbralResponse(
                producto.id,
                producto.codigoProducto,
                producto.nombre,
                cantidad
        );
    }
}
