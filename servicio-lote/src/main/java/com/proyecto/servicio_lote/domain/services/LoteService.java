package com.proyecto.servicio_lote.domain.services;

import com.proyecto.servicio_lote.app.rest.request.LoteRequest;
import com.proyecto.servicio_lote.app.rest.request.ProductoCantidadRequest;
import com.proyecto.servicio_lote.app.rest.response.LoteResponse;
import com.proyecto.servicio_lote.clients.ProductoFeignClient;
import com.proyecto.servicio_lote.common.enums.TipoMovimiento;
import com.proyecto.servicio_lote.domain.models.Kardex;
import com.proyecto.servicio_lote.domain.models.Lote;
import com.proyecto.servicio_lote.domain.models.Proveedor;
import com.proyecto.servicio_lote.domain.repositories.KardexRepository;
import com.proyecto.servicio_lote.domain.repositories.LoteRepository;
import com.proyecto.servicio_lote.domain.repositories.ProductoRepository;
import com.proyecto.servicio_lote.domain.repositories.ProveedorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Transactional
public class LoteService {

    private final ProductoFeignClient productoFeignClient;
    private final LoteRepository loteRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;
    private final KardexRepository kardexRepository;

    public LoteResponse registrarLote(LoteRequest request){
        var proveedor = proveedorRepository.findById(request.proveedorId()).orElseThrow(() -> new RuntimeException("No existe el proveedor"));
        var producto = productoRepository.findById(request.productoId()).orElseThrow(() -> new RuntimeException("No existe el producto"));

        ProductoCantidadRequest requestCantidad = new ProductoCantidadRequest(producto.getCantidadStock() + request.cantidad());
        productoFeignClient.actualizarStock(producto.getId(), requestCantidad);

        Lote lote = loteRepository.save(Lote.builder()
                .producto(producto)
                .proveedor(proveedor)
                .cantidad(request.cantidad())
                .fechaAdquisicion(LocalDate.now())
                .fechaExpiracion(request.fechaExpiracion())
                .build());

        kardexRepository.save(Kardex.builder()
                .productoId(producto.getId())
                .tipoMovimiento(TipoMovimiento.COMPRA)
                .cantidad(request.cantidad())
                .fechaMovimiento(LocalDate.now())
                .precioUnitario(request.precioUnitario())
                .loteId(lote.getId())
                .proveedorId(proveedor.getId())
                .build());
        
        return Lote.aResponse(lote);
    }
}
