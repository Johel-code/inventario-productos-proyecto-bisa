package com.proyecto.servicio_producto.domain.services.abstract_service;

import com.proyecto.servicio_producto.app.rest.request.ProductoRequest;
import com.proyecto.servicio_producto.app.rest.response.ProductoResponse;

public interface IProductoService extends CrudService<ProductoRequest, ProductoResponse, Long> {
}
