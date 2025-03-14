package com.proyecto.servicio_producto.app.rest.controllers;

import com.proyecto.servicio_producto.app.rest.request.ProductoCostoCompraRequest;
import com.proyecto.servicio_producto.app.rest.request.ProductoRequest;
import com.proyecto.servicio_producto.app.rest.response.ProductoResponse;
import com.proyecto.servicio_producto.domain.services.impl.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "producto")
@AllArgsConstructor
public class ProductoController {

    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> mostrarProductos() {
        return ResponseEntity.ok(productoService.mostrarTodo());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ProductoResponse> mostrarProductoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.mostrarPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<ProductoResponse> crearProducto(@RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(request));
    }
    
    @PutMapping(path = "{id}")
    public ResponseEntity<ProductoResponse> actualizarProducto(@RequestBody ProductoRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(productoService.actualizar(request,id));
    }
    
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

//    @PutMapping(path = "actualizar-stock/{id}")
//    public ResponseEntity<Void> actualizarStock(@PathVariable Long id, @RequestBody ProductoCantidadRequest request) {
//        productoService.actualizarStock(id, request);
//        return ResponseEntity.ok().build();
//    }

    @PutMapping(path = "actualizar-costo-compra/{id}")
    public ResponseEntity<Void> actualizarCostoCompra(@PathVariable Long id, @RequestBody ProductoCostoCompraRequest request) {
        productoService.actualizarCostoCompra(id);
        return ResponseEntity.ok().build();

    }
}