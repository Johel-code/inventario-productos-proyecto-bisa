package com.proyecto.servicio_venta.domain.services;

import com.proyecto.servicio_venta.app.rest.request.DetalleVentaRequest;
import com.proyecto.servicio_venta.app.rest.request.LoteCantidadRequest;
import com.proyecto.servicio_venta.app.rest.request.VentaRequest;
import com.proyecto.servicio_venta.app.rest.response.DetalleVentaResponse;
import com.proyecto.servicio_venta.app.rest.response.VentaResponse;
import com.proyecto.servicio_venta.clients.LoteFeignClient;
import com.proyecto.servicio_venta.clients.ProductoFeignClient;
import com.proyecto.servicio_venta.common.enums.TipoMovimiento;
import com.proyecto.servicio_venta.common.exceptions.CantidadInsuficienteException;
import com.proyecto.servicio_venta.common.exceptions.IdNotFoudException;
import com.proyecto.servicio_venta.common.exceptions.PrecioNoValidoException;
import com.proyecto.servicio_venta.domain.models.*;
import com.proyecto.servicio_venta.domain.repositories.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class VentaService {

    private final ProductoRepository productoRepository;
    private final LoteFeignClient loteFeignClient;
    private final LoteRepository loteRepository;
    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final KardexRepository kardexRepository;

    public VentaResponse registrarVenta(VentaRequest request){
        List<DetalleVentaResponse> detallesDeVenta = new ArrayList<>();
        var venta = ventaRepository.save(Venta.builder()
                .fecha(LocalDate.now())
                .totalVenta(BigDecimal.ZERO)
                .build());

        BigDecimal totalVenta = BigDecimal.ZERO;

        for (DetalleVentaRequest requestActual : request.detalleVenta()) {
            Producto producto = productoRepository.findById(requestActual.productoId()).orElseThrow(() -> new IdNotFoudException("Producto"));

            Integer cantidadRequerida = requestActual.cantidad();
            BigDecimal precioVenta = (requestActual.precioUnitario()==null)?producto.getPrecioVenta():requestActual.precioUnitario();
            totalVenta = totalVenta.add(precioVenta.multiply(BigDecimal.valueOf(cantidadRequerida)));

            producto.validarPrecio(precioVenta);

            DetalleVenta detalleVenta = detalleVentaRepository.save(DetalleVenta.builder()
                    .productoId(producto.getId())
                    .venta(venta)
                    .cantidad(cantidadRequerida)
                    .precioUnitario(precioVenta)
                    .build());

            detallesDeVenta.add(DetalleVenta.aResponse(detalleVenta));

            //List<Lote> lotes = loteFeignClient.obtenerLotesOrdenadosPorVencimiento(producto.getId());
            List<Lote> lotes = new ArrayList<>(producto.getLotes());
            lotes.sort(Comparator.comparing(Lote::getFechaExpiracion));
            log.info("Lotes de lotes por vencimiento" + lotes);

            for(Lote lote : lotes){
                log.info("Lote " + lote);

                if(cantidadRequerida <= 0) break;
                if(lote.getCantidad() == 0) continue;

                int cantidadATomar = Math.min(cantidadRequerida, lote.getCantidad());

                int cantidadStockLote = lote.getCantidad() - cantidadATomar;
                log.info("Producto " + producto);
                lote.setCantidad(cantidadStockLote);
                //loteFeignClient.actualizarStock(lote.getId(), new LoteCantidadRequest(cantidadStockLote));
                log.info("Producto2 " + producto);
                if(cantidadStockLote == 0) producto.actualizarCostoCompra();
                log.info("Lote " + lote);


                kardexRepository.save(Kardex.builder()
                        .productoId(producto.getId())
                        .tipoMovimiento(TipoMovimiento.VENTA)
                        .cantidad(cantidadATomar)
                        .fechaMovimiento(LocalDate.now())
                        .costoCompra(lote.getCostoCompra())
                        .precioVenta(precioVenta)
                        .ventaId(venta.getId())
                        .loteId(lote.getId())
                        .build());

                cantidadRequerida -= cantidadATomar;

                //lote.setProducto(producto);
                loteRepository.save(lote);
                log.info("salvando lote " + lote);
            }

            if(cantidadRequerida > 0){
                throw new CantidadInsuficienteException(producto.getNombre());
            }

//            ProductoCantidadRequest requestCantidad = new ProductoCantidadRequest(producto.getCantidadStock() - requestActual.cantidad());
//            productoFeignClient.actualizarStock(producto.getId(), requestCantidad);
        }


        venta.setTotalVenta(totalVenta);
        ventaRepository.save(venta);

        log.info("fin");

        return Venta.aResponse(venta, detallesDeVenta);
    }

}
