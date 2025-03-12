package com.proyecto.servicio_venta.domain.models;

import com.proyecto.servicio_venta.common.enums.TipoMovimiento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kardex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productoId;

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
