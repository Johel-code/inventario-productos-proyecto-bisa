package com.proyecto.servicio_lote.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Long productoId;
    private Integer cantidad;
    private LocalDate fechaAdquisicion;
    private LocalDate fechaExpiracion;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

}
