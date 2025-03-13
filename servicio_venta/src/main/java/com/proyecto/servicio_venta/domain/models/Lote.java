package com.proyecto.servicio_venta.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Lote {

    @Id
    private Long id;

    private Long productoId;
    private Long proveedorId;
    private Integer cantidad;
    private BigDecimal costoCompra;
    private LocalDate fechaAdquisicion;
    private LocalDate fechaExpiracion;

}
