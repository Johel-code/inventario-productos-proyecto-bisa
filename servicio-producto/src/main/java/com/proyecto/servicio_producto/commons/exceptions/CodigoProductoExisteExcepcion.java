package com.proyecto.servicio_producto.commons.exceptions;

public class CodigoProductoExisteExcepcion extends RuntimeException {

    private static final String ERROR_MESSAGE = "El producto ya existe en la base de datos";

    public CodigoProductoExisteExcepcion() {
        super(ERROR_MESSAGE);
    }
}
