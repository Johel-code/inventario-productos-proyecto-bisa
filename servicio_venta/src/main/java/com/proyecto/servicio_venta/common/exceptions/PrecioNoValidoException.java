package com.proyecto.servicio_venta.common.exceptions;

import com.proyecto.servicio_venta.common.enums.ErrorMsg;

public class PrecioNoValidoException extends BaseException{


    public PrecioNoValidoException(String producto) {
        super(ErrorMsg.PRECIO_NO_VALIDO, producto);
    }
}
