package com.proyecto.servicio_producto.domain.services.abstract_service;

import java.util.List;

public interface CrudService<RQ, RS, ID> {

    List<RS> mostrarTodo();
    RS mostrarPorId(ID id);
    RS crear(RQ request);
    RS actualizar(RQ request, ID id);
    void eliminar(ID id);

}
