package com.proyecto.servicio_lote.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoProducto;
    private String nombre;
    private BigDecimal costoCompra;
    private BigDecimal precioVenta;
    private Integer cantidadStock;
    private Integer minStock;

    @OneToMany(mappedBy = "producto")
    private List<Lote> lotes;
}
