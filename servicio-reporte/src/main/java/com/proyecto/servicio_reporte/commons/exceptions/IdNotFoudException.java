package com.proyecto.servicio_reporte.commons.exceptions;


import com.proyecto.servicio_reporte.commons.enums.ErrorMsg;

public class IdNotFoudException extends BaseException{


    public IdNotFoudException(String tableName) {
        super(ErrorMsg.ID_NOT_FOUND, tableName);
    }
}
