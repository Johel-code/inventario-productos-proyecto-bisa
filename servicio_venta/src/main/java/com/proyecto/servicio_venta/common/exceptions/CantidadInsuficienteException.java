package com.proyecto.servicio_venta.common.exceptions;

public class CantidadInsuficienteException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Cantidad en stock insuficiente para el producto: %s";

    public CantidadInsuficienteException(String producto) {
        super(String.format(ERROR_MESSAGE, producto));
    }
}
