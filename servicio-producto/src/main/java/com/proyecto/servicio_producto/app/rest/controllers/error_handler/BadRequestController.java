package com.proyecto.servicio_producto.app.rest.controllers.error_handler;

import com.proyecto.servicio_producto.app.rest.response.BaseErrorResponse;
import com.proyecto.servicio_producto.app.rest.response.ErrorResponse;
import com.proyecto.servicio_producto.app.rest.response.ErrorsResponse;
import com.proyecto.servicio_producto.commons.exceptions.CodigoProductoExisteExcepcion;
import com.proyecto.servicio_producto.commons.exceptions.IdNotFoudException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {

    @ExceptionHandler(IdNotFoudException.class)
    public BaseErrorResponse handleIdNotFoudException(IdNotFoudException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(CodigoProductoExisteExcepcion.class)
    public BaseErrorResponse handleCodigoProductoExisteExcepcion(CodigoProductoExisteExcepcion e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handleBadRequestException(MethodArgumentNotValidException e) {
        var errors = new ArrayList<String>();
        e.getAllErrors()
                .forEach(error -> errors.add(error.getDefaultMessage()));
        return ErrorsResponse.builder()
                .errors(errors)
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
