package com.proyecto.servicio_venta.common.exceptions;

import com.proyecto.servicio_venta.common.enums.ErrorMsg;

public class CantidadInsuficienteException extends BaseException {

    public CantidadInsuficienteException(String producto) {
        super(ErrorMsg.CANTIDAD_INSUFICIENTE, producto);
    }
}
