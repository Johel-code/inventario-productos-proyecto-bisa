package com.proyecto.servicio_lote.domain.models;

import com.proyecto.servicio_lote.app.rest.response.ProveedorResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @OneToMany(mappedBy = "proveedor")
    private List<Lote> lotes;

    public static ProveedorResponse aResponse(Proveedor proveedor) {
        return new ProveedorResponse(
                proveedor.id,
                proveedor.nombre,
                proveedor.direccion
        );
    }
}
