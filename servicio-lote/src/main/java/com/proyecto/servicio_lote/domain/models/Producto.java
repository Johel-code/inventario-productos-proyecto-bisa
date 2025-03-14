package com.proyecto.servicio_lote.domain.models;

import com.proyecto.servicio_lote.app.rest.response.ProductoResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
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

    @ToString.Exclude
    @OneToMany(mappedBy = "producto")
    private List<Lote> lotes;

    public static ProductoResponse aResponse(Producto producto) {
        return new ProductoResponse(
                producto.id,
                producto.codigoProducto,
                producto.nombre,
                producto.costoCompra,
                producto.precioVenta,
                producto.minStock,
                producto.porcentajeGanancia
        );
    }

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

    public void actualizarCostoCompra() {
        var lotes = this.getLotes();

        BigDecimal sumaCostosPonderados = BigDecimal.ZERO;

        for(Lote lote : lotes) {
            sumaCostosPonderados = sumaCostosPonderados.add(
                    lote.getCostoCompra().multiply(BigDecimal.valueOf(lote.getCantidad()))
            );
        }
        int sumaCantidades = getCantidadStock();

        BigDecimal nuevoCostoCompra = sumaCantidades == 0 ? BigDecimal.ZERO :
                sumaCostosPonderados.divide(BigDecimal.valueOf(sumaCantidades), 2, RoundingMode.HALF_UP);

        setCostoCompra(nuevoCostoCompra);
        log.info("En servicio producto, costo de compra actualizado: " + nuevoCostoCompra);
        setPrecioVenta(Producto.calcularPrecioVenta(nuevoCostoCompra, getPorcentajeGanancia()));
        log.info("En servicio producto, precio de venta actualizado: " + getPrecioVenta());
    }

    private final static Double IMPUESTO_IVA = 0.13;
    private final static Double IMPUESTO_IT  = 0.03;
}
