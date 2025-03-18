package com.proyecto.servicio_producto.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMsg {

    REQUEST_INVALIDO("ERROR-001", "Error en los datos enviados", HttpStatus.BAD_REQUEST),
    JSON_PROCESING("ERROR-002", "Error al procesar json", HttpStatus.NOT_FOUND),
    SERIALIZACION_ERROR("ERROR-003", "Error al procesar la respuesta, verifique la estructura de datos", HttpStatus.INTERNAL_SERVER_ERROR),
    FEIGN_ERROR("ERROR-004", "Error al llamar al servicio externo", HttpStatus.INTERNAL_SERVER_ERROR),
    ID_NOT_FOUND("ERROR-010", "El id no existe en %s", HttpStatus.NOT_FOUND),
    CANTIDAD_INSUFICIENTE("ERROR-011", "Cantidad en stock insuficiente para el producto: %s", HttpStatus.BAD_REQUEST),
    PRECIO_NO_VALIDO("ERROR-012", "Precio de venta no valido para el producto: %s", HttpStatus.BAD_REQUEST),
    CODIGO_PRODUCTO_EXISTENTE("ERROR-013", "El codigo de producto ya existe", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("ERROR-099", "Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String codigo;
    private final String mensaje;
    private final HttpStatus status;

    public String getFormateMessage(String entidad) {
        return String.format(mensaje, entidad);
    }
}
