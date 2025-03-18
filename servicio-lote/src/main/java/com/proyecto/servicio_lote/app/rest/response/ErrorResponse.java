package com.proyecto.servicio_lote.app.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public record ErrorResponse(
        String codigo,
        String mensaje,
        @JsonIgnore
        HttpStatus status
) {
}
