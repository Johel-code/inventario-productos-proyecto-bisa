package com.proyecto.servicio_venta.domain.models;

import com.proyecto.servicio_venta.common.enums.TipoMovimiento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private TipoMovimiento tipoMovimiento;
    private Integer cantidad;
    private LocalDate fechaMovimiento;
    private BigDecimal precioUnitario;
    private Long ventaId;
    private Long proveedorId;
    private String razonMovimiento;
}
