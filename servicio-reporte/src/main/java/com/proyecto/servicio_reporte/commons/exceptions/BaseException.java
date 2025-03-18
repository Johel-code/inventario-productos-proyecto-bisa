package com.proyecto.servicio_reporte.commons.exceptions;

import com.proyecto.servicio_reporte.commons.enums.ErrorMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private final String codigo;
    private final HttpStatus status;

    public BaseException(ErrorMsg error, String entidad) {
        super(error.getFormateMessage(entidad));
        codigo = error.getCodigo();
        status = error.getStatus();
    }

}
