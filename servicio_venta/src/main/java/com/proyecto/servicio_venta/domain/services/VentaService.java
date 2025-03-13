package com.proyecto.servicio_venta.domain.services;

import com.proyecto.servicio_venta.app.rest.request.DetalleVentaRequest;
import com.proyecto.servicio_venta.app.rest.request.ProductoCantidadRequest;
import com.proyecto.servicio_venta.app.rest.request.VentaRequest;
import com.proyecto.servicio_venta.app.rest.response.DetalleVentaResponse;
import com.proyecto.servicio_venta.app.rest.response.VentaResponse;
import com.proyecto.servicio_venta.clients.ProductoFeignClient;
import com.proyecto.servicio_venta.common.enums.TipoMovimiento;
import com.proyecto.servicio_venta.domain.models.DetalleVenta;
import com.proyecto.servicio_venta.domain.models.Kardex;
import com.proyecto.servicio_venta.domain.models.Producto;
import com.proyecto.servicio_venta.domain.models.Venta;
import com.proyecto.servicio_venta.domain.repositories.DetalleVentaRepository;
import com.proyecto.servicio_venta.domain.repositories.KardexRepository;
import com.proyecto.servicio_venta.domain.repositories.VentaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class VentaService {

    private final ProductoFeignClient productoFeignClient;
    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final KardexRepository kardexRepository;

    public VentaResponse registrarVenta(VentaRequest request){
        List<DetalleVentaResponse> detallesDeVenta = new ArrayList<>();
        var venta = ventaRepository.save(Venta.builder()
                .fecha(LocalDate.now())
                .totalVenta(request.totalVenta())
                .build());

        for (DetalleVentaRequest actual : request.detalleVenta()) {
            Producto producto = productoFeignClient.mostrarProductoPorId(actual.productoId());

            Integer cantidadRequerida = actual.cantidad();
            BigDecimal precioVenta = (actual.precioUnitario()==null)?producto.getPrecioVenta():actual.precioUnitario();
            totalVenta = totalVenta.add(precioVenta.multiply(BigDecimal.valueOf(cantidadRequerida)));

            validarVenta(producto, precioVenta, cantidadRequerida);

            DetalleVenta detalleVenta = detalleVentaRepository.save(DetalleVenta.builder()
                    .productoId(producto.getId())
                    .venta(venta)
                    .cantidad(cantidadRequerida)
                    .precioUnitario(precioVenta)
                    .build());

            detallesDeVenta.add(DetalleVenta.aResponse(detalleVenta));

            List<Lote> lotes = loteFeignClient.obtenerLotesOrdenadosPorVencimiento(producto.getId());

            for(Lote lote : lotes){

                if(cantidadRequerida <= 0) break;

                int cantidadATomar = Math.min(cantidadRequerida, lote.getCantidad());
                loteFeignClient.actualizarStock(lote.getId(), new LoteCantidadRequest(lote.getCantidad() - cantidadATomar));

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
            }

            ProductoCantidadRequest requestCantidad = new ProductoCantidadRequest(producto.getCantidadStock() - actual.cantidad());
            productoFeignClient.actualizarStock(producto.getId(), requestCantidad);

            kardexRepository.save(Kardex.builder()
                    .productoId(producto.getId())
                    .tipoMovimiento(TipoMovimiento.VENTA)
                    .cantidad(actual.cantidad())
                    .fechaMovimiento(LocalDate.now())
                    .costoCompra(producto.getCostoCompra())
                    .precioVenta(actual.precioUnitario())
                    .ventaId(venta.getId())
                    .loteId(3L)
                    .build());
        }

        venta.setTotalVenta(totalVenta);
        ventaRepository.save(venta);

        return Venta.aResponse(venta, detallesDeVenta);
    }

    private void validarVenta(Producto producto, DetalleVentaRequest actual) {
        if (producto.getCantidadStock() < actual.cantidad()) throw new RuntimeException("Cantidad en stock insuficiente");

        BigDecimal precioVenta = (actual.precioUnitario()==null)?producto.getPrecioVenta():actual.precioUnitario();
        BigDecimal costoCompra = producto.getCostoCompra();
        BigDecimal precioMinimo = costoCompra.multiply(BigDecimal.valueOf(0.75));
        BigDecimal precioMaximo = costoCompra.multiply(BigDecimal.valueOf(1.75));

        if (precioVenta.compareTo(precioMinimo) < 0 || precioVenta.compareTo(precioMaximo) > 0) {
            throw new RuntimeException("Precio de venta no valido");
        }
    }

}
