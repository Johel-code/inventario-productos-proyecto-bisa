package com.proyecto.servicio_lote.app.rest.request;

import java.math.BigDecimal;

public record ProductoCostoCompraRequest(
        BigDecimal costoCompra
) {
}
