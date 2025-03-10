package com.proyecto.servicio_venta.domain.models;

import com.proyecto.servicio_venta.app.rest.response.DetalleVentaResponse;
import com.proyecto.servicio_venta.app.rest.response.VentaResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private BigDecimal totalVenta;

    @OneToMany(mappedBy = "venta")
    private List<DetalleVenta> detalleVenta;

    public static VentaResponse aResponse(Venta venta, List<DetalleVentaResponse> detallesDeVenta) {
        return new VentaResponse(
                venta.id,
                venta.fecha,
                venta.totalVenta,
                detallesDeVenta
        );
    }
}
