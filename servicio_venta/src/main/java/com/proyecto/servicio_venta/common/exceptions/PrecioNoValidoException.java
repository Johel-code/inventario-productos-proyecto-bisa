package com.proyecto.servicio_venta.common.exceptions;

public class PrecioNoValidoException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Precio de venta no valido para el producto: %s";

    public PrecioNoValidoException(String producto) {
        super(String.format(ERROR_MESSAGE, producto));
    }
}
