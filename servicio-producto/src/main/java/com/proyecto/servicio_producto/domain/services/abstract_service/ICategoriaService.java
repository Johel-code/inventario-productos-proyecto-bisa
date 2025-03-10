package com.proyecto.servicio_producto.domain.services.abstract_service;

import com.proyecto.servicio_producto.app.rest.request.CategoriaRequest;
import com.proyecto.servicio_producto.app.rest.response.CategoriaResponse;

public interface ICategoriaService extends CrudService<CategoriaRequest, CategoriaResponse, Long> {
}
