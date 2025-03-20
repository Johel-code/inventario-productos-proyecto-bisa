package com.proyecto.servicio_venta.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String direccion;
    @ToString.Exclude
    @OneToMany(mappedBy = "proveedor")
    private List<Lote> lotes;

}
