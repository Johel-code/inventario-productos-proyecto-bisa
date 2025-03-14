package com.proyecto.servicio_venta.app.rest.controllers.error_handler;

import com.proyecto.servicio_venta.app.rest.response.BaseErrorResponse;
import com.proyecto.servicio_venta.app.rest.response.ErrorResponse;
import com.proyecto.servicio_venta.app.rest.response.ErrorsResponse;
import com.proyecto.servicio_venta.common.exceptions.CantidadInsuficienteException;
import com.proyecto.servicio_venta.common.exceptions.IdNotFoudException;
import com.proyecto.servicio_venta.common.exceptions.PrecioNoValidoException;
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

    @ExceptionHandler(CantidadInsuficienteException.class)
    public BaseErrorResponse handleCantidadInsuficienteExcepcion(CantidadInsuficienteException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(PrecioNoValidoException.class)
    public BaseErrorResponse handlePrecioNoValidoExcepcion(PrecioNoValidoException e) {
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
