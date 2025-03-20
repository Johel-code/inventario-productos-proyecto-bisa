package com.proyecto.servicio_venta.common.exceptions;

import com.proyecto.servicio_venta.common.enums.ErrorMsg;

public class IdNotFoudException extends BaseException{


    public IdNotFoudException(String tableName) {
        super(ErrorMsg.ID_NOT_FOUND, tableName);
    }
}
