package com.proyecto.servicio_venta.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne()
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    private Integer cantidad;
    private BigDecimal costoCompra;
    private LocalDate fechaAdquisicion;
    private LocalDate fechaExpiracion;

}
