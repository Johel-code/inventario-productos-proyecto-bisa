package com.proyecto.servicio_producto.commons.exceptions;

import com.proyecto.servicio_producto.commons.enums.ErrorMsg;

public class CodigoProductoExisteExcepcion extends BaseException{


    public CodigoProductoExisteExcepcion() {
        super(ErrorMsg.CODIGO_PRODUCTO_EXISTENTE, "");
    }
}
