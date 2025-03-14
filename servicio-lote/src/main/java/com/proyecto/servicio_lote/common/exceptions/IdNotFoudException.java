package com.proyecto.servicio_lote.common.exceptions;

public class IdNotFoudException extends RuntimeException {

    private static final String ERROR_MESSAGE = "El id no existe en %s";

    public IdNotFoudException(String tableName) {
        super(String.format(ERROR_MESSAGE, tableName));
    }
}
