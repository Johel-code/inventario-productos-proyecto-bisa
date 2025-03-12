package com.proyecto.servicio_reporte.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DetalleVenta {

    @Id
    private Long id;
    private Integer cantidad;
    private BigDecimal precioUnitario;

//    @ManyToOne
//    @JoinColumn(name = "venta_id")
//    private Venta venta;
//
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

//    public static DetalleVentaResponse aResponse(DetalleVenta detalleVenta) {
//        return new DetalleVentaResponse(
//            detalleVenta.productoId,
//            detalleVenta.cantidad,
//            detalleVenta.precioUnitario,
//            detalleVenta.precioUnitario.multiply(BigDecimal.valueOf(detalleVenta.cantidad))
//        );
//    }
}
