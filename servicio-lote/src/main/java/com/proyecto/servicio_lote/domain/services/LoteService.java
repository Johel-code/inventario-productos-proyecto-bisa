package com.proyecto.servicio_lote.domain.services;

import com.proyecto.servicio_lote.app.rest.request.LoteCantidadRequest;
import com.proyecto.servicio_lote.app.rest.request.LoteRequest;
import com.proyecto.servicio_lote.app.rest.request.ProductoCantidadRequest;
import com.proyecto.servicio_lote.app.rest.response.LoteResponse;
import com.proyecto.servicio_lote.clients.ProductoFeignClient;
import com.proyecto.servicio_lote.common.enums.TipoMovimiento;
import com.proyecto.servicio_lote.domain.models.Kardex;
import com.proyecto.servicio_lote.domain.models.Lote;
import com.proyecto.servicio_lote.domain.models.Producto;
import com.proyecto.servicio_lote.domain.models.Proveedor;
import com.proyecto.servicio_lote.domain.repositories.KardexRepository;
import com.proyecto.servicio_lote.domain.repositories.LoteRepository;
import com.proyecto.servicio_lote.domain.repositories.ProductoRepository;
import com.proyecto.servicio_lote.domain.repositories.ProveedorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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


        Lote lote = loteRepository.save(Lote.builder()
                .producto(producto)
                .proveedor(proveedor)
                .cantidad(request.cantidad())
                .fechaAdquisicion(LocalDate.now())
                .fechaExpiracion(request.fechaExpiracion())
                .build());

        actualizarStockProducto(request, producto);
        actualizarKardex(request, producto, lote, proveedor);

        return Lote.aResponse(lote);
    }

    public List<LoteResponse> obtenerLotesPorIdProducto(Long productoId){
        var producto = productoRepository.findById(productoId).orElseThrow(() -> new RuntimeException("No existe el producto"));
        var lotes = loteRepository.findByProducto(producto);
        return lotes.stream()
                .map(Lote::aResponse)
                .toList();
    }

    public void actualizarStockLote(Long id, LoteCantidadRequest request){
        var lote = loteRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe el lote"));
        lote.setCantidad(request.cantidad());
        loteRepository.save(lote);
    }

    private void actualizarStockProducto(LoteRequest request, Producto producto) {
        ProductoCantidadRequest requestCantidad = new ProductoCantidadRequest(producto.getCantidadStock() + request.cantidad());
        productoFeignClient.actualizarStock(producto.getId(), requestCantidad);
    }

    private void actualizarKardex(LoteRequest request, Producto producto, Lote lote, Proveedor proveedor) {
        kardexRepository.save(Kardex.builder()
                .productoId(producto.getId())
                .tipoMovimiento(TipoMovimiento.COMPRA)
                .cantidad(request.cantidad())
                .fechaMovimiento(LocalDate.now())
                .costoCompra(request.precioUnitario())
                .loteId(lote.getId())
                .proveedorId(proveedor.getId())
                .build());
    }
}
