package com.proyecto.servicio_reporte.domain.models;

import com.proyecto.servicio_reporte.commons.enums.TipoMovimiento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Kardex {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipoMovimiento;

    private Integer cantidad;
    private LocalDate fechaMovimiento;
    private BigDecimal costoCompra;
    private BigDecimal precioVenta;
    private Long ventaId;
    private Long loteId;
    private Long proveedorId;
    private String razonMovimiento;
}
