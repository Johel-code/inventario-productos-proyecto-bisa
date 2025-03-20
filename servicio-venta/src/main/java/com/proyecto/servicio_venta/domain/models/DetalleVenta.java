package com.proyecto.servicio_venta.domain.models;

import com.proyecto.servicio_venta.app.rest.response.DetalleVentaResponse;
import com.proyecto.servicio_venta.app.rest.response.ProductoDTO;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    private BigDecimal precioUnitario;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    private Long productoId;

    public static DetalleVentaResponse aResponse(DetalleVenta detalleVenta) {
        return new DetalleVentaResponse(
            detalleVenta.productoId,
            detalleVenta.cantidad,
            detalleVenta.precioUnitario,
            detalleVenta.precioUnitario.multiply(BigDecimal.valueOf(detalleVenta.cantidad))
        );
    }
}
