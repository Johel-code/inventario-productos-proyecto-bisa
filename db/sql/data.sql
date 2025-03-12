-- Insertar categorías falsas
INSERT INTO categoria (nombre) VALUES
    ('Bebidas'),
    ('Alimentos'),
    ('Limpieza'),
    ('Electrónica');

-- Insertar proveedores falsos
INSERT INTO proveedor (nombre, direccion) VALUES
    ('Proveedor A', 'Calle 123, Ciudad A'),
    ('Proveedor B', 'Avenida 456, Ciudad B'),
    ('Proveedor C', 'Carrera 789, Ciudad C');

-- Insertar productos falsos
-- INSERT INTO producto (codigo_producto, nombre, min_stock, porcentaje_ganancia, categoria_id) VALUES
--     ('PROD001', 'Leche', 50, 20.00, 1),       -- Categoría: Bebidas
--     ('PROD002', 'Pan', 100, 15.00, 2),         -- Categoría: Alimentos
--     ('PROD003', 'Detergente', 30, 25.00, 3),   -- Categoría: Limpieza
--     ('PROD004', 'Cargador USB', 20, 30.00, 4); -- Categoría: Electrónica

-- Insertar stock falsos
-- INSERT INTO lote (producto_id, proveedor_id, costo_compra, cantidad, fecha_adquisicion, fecha_expiracion) VALUES
--     (1, 1, 2.50, 100, '2023-10-01', '2024-01-01'),  -- Leche del Proveedor A
--     (2, 2, 1.00, 200, '2023-10-05', '2023-12-15'),  -- Pan del Proveedor B
--     (3, 3, 3.00, 150, '2023-10-10', '2024-02-01'),  -- Detergente del Proveedor C
--     (4, 1, 5.00, 50, '2023-10-15', NULL);           -- Cargador USB del Proveedor A (sin fecha de vencimiento)

-- Insertar ventas falsas
-- INSERT INTO venta (fecha, total_venta) VALUES
--     ('2023-10-15', 10.50),
--     ('2023-10-16', 15.00);

-- Insertar detalles de venta falsos
-- INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES
--     (1, 1, 2, 3.50),  -- Venta 1: 2 unidades de Leche
--     (1, 2, 3, 1.50),  -- Venta 1: 3 unidades de Pan
--     (2, 3, 1, 4.00);  -- Venta 2: 1 unidad de Detergente

-- Insertar movimientos en el Kardex
-- INSERT INTO kardex (producto_id, tipo_movimiento, cantidad, fecha_movimiento, precio_unitario, venta_id, lote_id, proveedor_id, razon_movimiento) VALUES
--     (1, 'entry', 100, '2023-10-01', 2.50, NULL, 1, 1, 'Compra inicial de Leche'),
--     (2, 'entry', 200, '2023-10-05', 1.00, NULL, 2, 2, 'Compra inicial de Pan'),
--     (3, 'entry', 150, '2023-10-10', 3.00, NULL, 3, 3, 'Compra inicial de Detergente'),
--     (4, 'entry', 50, '2023-10-15', 5.00, NULL, 4, 1, 'Compra inicial de Cargador USB'),
--    (1, 'exit', 2, '2023-10-15', 3.50, 1, 1, NULL, 'Venta de Leche'),
--    (2, 'exit', 3, '2023-10-15', 1.50, 1, 2, NULL, 'Venta de Pan'),
--    (3, 'exit', 1, '2023-10-16', 4.00, 2, 3, NULL, 'Venta de Detergente');
