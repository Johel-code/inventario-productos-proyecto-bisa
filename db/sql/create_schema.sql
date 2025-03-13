CREATE TABLE categoria
(
 "id"   bigserial NOT NULL,
 nombre varchar(20) NOT NULL,
 CONSTRAINT PK_2 PRIMARY KEY ( "id" )
);

CREATE TABLE producto
(
 "id"               bigserial NOT NULL,
 codigo_producto    varchar(15) NOT NULL UNIQUE,
 nombre             varchar(50) NOT NULL,
 costo_compra       double precision NOT NULL,
 precio_venta       double precision NOT NULL,
 cantidad_stock     int NOT NULL DEFAULT 0,
 min_stock          int NOT NULL,
 porcentaje_ganancia  decimal(5, 2) NOT NULL,
 categoria_id         bigint NOT NULL,
 CONSTRAINT PK_1 PRIMARY KEY ( "id" ),
 CONSTRAINT FK_1 FOREIGN KEY ( categoria_id ) REFERENCES categoria ( "id" )
);

CREATE TABLE venta
(
 "id"       bigserial NOT NULL,
 fecha      date NOT NULL,
 total_venta double precision NOT NULL,
 CONSTRAINT PK_4 PRIMARY KEY ( "id" )
);

CREATE TABLE proveedor
(
 "id"    bigserial NOT NULL,
 nombre  varchar(50) NOT NULL,
 direccion varchar(50) NOT NULL,
 CONSTRAINT PK_3 PRIMARY KEY ( "id" )
);

CREATE TABLE detalle_venta
(
 "id"         bigserial NOT NULL,
 venta_id     bigserial NOT NULL,
 producto_id  bigint NOT NULL,
 cantidad     int NOT NULL,
 precio_unitario double precision NOT NULL,
 CONSTRAINT PK_5 PRIMARY KEY ( "id" ),
 CONSTRAINT FK_1 FOREIGN KEY ( venta_id ) REFERENCES venta ( "id" ),
 CONSTRAINT FK_2 FOREIGN KEY ( producto_id ) REFERENCES producto ( "id" )
);

CREATE TABLE lote
(
 "id"             bigserial NOT NULL,
 producto_id       bigserial NOT NULL,
 proveedor_id      bigserial NOT NULL,
 cantidad           int NOT NULL,
 costo_compra     double precision NOT NULL,
 fecha_adquisicion  date NOT NULL,
 fecha_expiracion   date NULL,
 CONSTRAINT PK_6 PRIMARY KEY ( "id" ),
 CONSTRAINT FK_1 FOREIGN KEY ( producto_id ) REFERENCES producto ( "id" ),
 CONSTRAINT FK_2 FOREIGN KEY ( proveedor_id ) REFERENCES proveedor ( "id" )
);

CREATE TABLE kardex
(
 "id"             bigserial NOT NULL,
 producto_id      bigint NOT NULL,
 tipo_movimiento  varchar(15) NOT NULL,
 cantidad         int NOT NULL,
 fecha_movimiento date NOT NULL,
 costo_compra     double precision NOT NULL,
 precio_venta     double precision NULL,
 venta_id         bigint NULL,
 lote_id          bigint NOT NULL,
 proveedor_id     bigint NULL,
 razon_movimiento text NULL,
 CONSTRAINT PK_7 PRIMARY KEY ( "id" ),
 CONSTRAINT FK_1 FOREIGN KEY ( producto_id ) REFERENCES producto ( "id" ),
 CONSTRAINT FK_2 FOREIGN KEY ( venta_id ) REFERENCES venta ( "id" ),
 CONSTRAINT FK_3 FOREIGN KEY ( lote_id ) REFERENCES lote ( "id" ),
 CONSTRAINT FK_4 FOREIGN KEY ( proveedor_id ) REFERENCES proveedor ( "id" )
);


