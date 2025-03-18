package com.proyecto.servicio_reporte.app.rest.controllers.error_handler;

import com.proyecto.servicio_reporte.app.rest.response.ErrorResponse;
import com.proyecto.servicio_reporte.commons.enums.ErrorMsg;
import com.proyecto.servicio_reporte.commons.exceptions.BaseException;
import feign.FeignException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Hidden
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException e) {
        return buildResponseEntity(new ErrorResponse(e.getCodigo(), e.getMessage(), e.getStatus()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return buildResponseEntity(new ErrorResponse(ErrorMsg.REQUEST_INVALIDO.getCodigo(), ErrorMsg.REQUEST_INVALIDO.getMensaje(), ErrorMsg.REQUEST_INVALIDO.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        return buildResponseEntity(new ErrorResponse(ErrorMsg.REQUEST_INVALIDO.getCodigo(), ErrorMsg.REQUEST_INVALIDO.getMensaje(), ErrorMsg.REQUEST_INVALIDO.getStatus()));
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<Object> handleHttpMessageNotWritableException(HttpMessageNotWritableException e) {
        log.error("Error posible en serializacion {}", e.getMessage());
        return buildResponseEntity(new ErrorResponse(ErrorMsg.SERIALIZACION_ERROR.getCodigo(), ErrorMsg.SERIALIZACION_ERROR.getMensaje(), ErrorMsg.SERIALIZACION_ERROR.getStatus()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignException(FeignException e) {
        log.error("Error al llamar a servicio externo {}" , e.getMessage());
        return buildResponseEntity(new ErrorResponse(ErrorMsg.FEIGN_ERROR.getCodigo(), ErrorMsg.FEIGN_ERROR.getMensaje(), HttpStatus.valueOf(e.status())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        log.error("Error interno del servidor {}", e.getMessage());
        return buildResponseEntity(new ErrorResponse(ErrorMsg.INTERNAL_ERROR.getCodigo(), ErrorMsg.INTERNAL_ERROR.getMensaje(), ErrorMsg.INTERNAL_ERROR.getStatus()));
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse error) {
        return new ResponseEntity<>(error, error.status());
    }

}
