package com.proyecto.servicio_venta.domain.models;

import com.proyecto.servicio_venta.common.exceptions.PrecioNoValidoException;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
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

    public int getCantidadStock() {
        log.info("getCantidadStock");
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
        log.info("actualizar costo compra" + getLotes());
        var lotes = this.getLotes();
        log.info("lotes " + lotes);

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
        setPrecioVenta(Producto.calcularPrecioVenta(nuevoCostoCompra, getPorcentajeGanancia()));
    }

    private final static Double IMPUESTO_IVA = 0.13;
    private final static Double IMPUESTO_IT  = 0.03;

    public void validarPrecio(BigDecimal precioVenta) {
        BigDecimal precioMinimo = costoCompra.multiply(BigDecimal.valueOf(0.75));
        BigDecimal precioMaximo = costoCompra.multiply(BigDecimal.valueOf(1.75));

        if (precioVenta.compareTo(precioMinimo) < 0 || precioVenta.compareTo(precioMaximo) > 0) {
            throw new PrecioNoValidoException(nombre);
        }
    }
}
