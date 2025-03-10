package com.proyecto.servicio_lote.domain.models;

import com.proyecto.servicio_lote.app.rest.response.LoteResponse;
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

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer cantidad;
    private LocalDate fechaAdquisicion;
    private LocalDate fechaExpiracion;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    public static LoteResponse aResponse(Lote lote) {
        return new LoteResponse(
            lote.id,
                lote.producto.getId(),
                lote.cantidad,
                lote.fechaAdquisicion,
                lote.fechaExpiracion,
                Proveedor.aResponse(lote.proveedor)
        );
    }
}
