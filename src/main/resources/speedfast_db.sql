-- Script de creación de la base de datos para ProyectoSpeedFastConexionDB

CREATE DATABASE IF NOT EXISTS speedfast_db;
USE speedfast_db;

-- Tabla repartidor
CREATE TABLE IF NOT EXISTS repartidor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla pedido
CREATE TABLE IF NOT EXISTS pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(255) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE'
);

-- Tabla entrega
CREATE TABLE IF NOT EXISTS entrega (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_repartidor INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id),
    FOREIGN KEY (id_repartidor) REFERENCES repartidor(id)
);
