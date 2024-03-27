CREATE DATABASE josemabalogin;
#DROP database josemabalogin;
USE josemabalogin;

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);
SELECT * FROM roles;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    password VARCHAR(255),
    role_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
SELECT * FROM users;

CREATE TABLE modules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    base_path VARCHAR(255)
);
SELECT * FROM  modules;

CREATE TABLE operations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    path VARCHAR(255),
    http_method VARCHAR(255),
    permit_all BOOLEAN,
    module_id BIGINT,
    FOREIGN KEY (module_id) REFERENCES modules(id)
);
SELECT * FROM operations;

CREATE TABLE granted_permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_id BIGINT,
    operation_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (operation_id) REFERENCES operations(id)
);
SELECT * FROM granted_permissions;

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    status ENUM('ENABLED', 'DISABLED')
);
SELECT * FROM categories;

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price DECIMAL(19, 2),
    status ENUM('ENABLED', 'DISABLED'),
    category_id BIGINT
);
SELECT * FROM products;

ALTER TABLE products ADD CONSTRAINT FK_products_categories FOREIGN KEY(category_id)
REFERENCES categories(id);


-- CREACION DE MODULOS
INSERT INTO modules (name, base_path) VALUES ('PRODUCT', '/products');
INSERT INTO modules (name, base_path) VALUES ('CATEGORY', '/categories');
INSERT INTO modules (name, base_path) VALUES ('CUSTOMER', '/customers');
INSERT INTO modules (name, base_path) VALUES ('AUTH', '/auth');


-- CREACION DE OPERACIONES
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PRODUCTS', '', 'GET', FALSE, 1);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_PRODUCT', '/[0-9]*', 'POST', FALSE, 1);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_PRODUCT', '', 'POST', FALSE, 1);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_PRODUCT', '/[0-9]*', 'PUT', FALSE, 1);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('DISABLE_ONE_PRODUCT', '/[0-9]*', 'PUT', FALSE, 1);

INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_CATEGORIES', '', 'GET', FALSE, 2);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_CATEGORY', '/[0-9]*', 'POST', FALSE, 2);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_CATEGORY', '', 'POST', FALSE, 2);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_CATEGORY', '/[0-9]*', 'PUT', FALSE, 2);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('DISABLE_ONE_CATEGORY', '/[0-9]*', 'PUT', FALSE, 2);

INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_CUSTOMERS', '', 'GET', FALSE, 3);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('REGISTER_ONE', '', 'POST', TRUE, 3);

INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('AUTHENTICATE', '/authenticate', 'POST', FALSE, 4);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('VALIDATE-TOKEN', '/validate-token', 'GET', FALSE, 4);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_MY_PROFILE', '/profile', 'GET', FALSE, 4);


-- CREACION DE ROLES
INSERT INTO roles (name) VALUES ('CUSTOMER');
INSERT INTO roles (name) VALUES ('ASSISTANT_ADMINISTRATOR');
INSERT INTO roles (name) VALUES ('ADMINISTRATOR');


-- CREACION DE PERMISOS
INSERT INTO granted_permissions (role_id, operation_id) VALUES (1, 15);

INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 1);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 2);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 4);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 6);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 7);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 9);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 15);


-- CREACIÓN DE USUARIOS
INSERT INTO users (username,name, password, role_id) VALUES ('jose', 'Jose Manuel', '$2a$10$BM1xGYNrqYNNzBd9LA7dB.qTh0we/UjYxFnKexfgO/yLZ67BPk3Qm', 3);
INSERT INTO users (username,name, password, role_id) VALUES ('rubi', 'Rubi Guadalupe', '$2a$10$K7C3gVm3s11G388M6HIAceIeutPuWhi20kebm.oTzWRsNNcrA1dVu', 2);
INSERT INTO users (username,name, password, role_id) VALUES ('margarita', 'Margarita', '$2a$10$wfu/o5skr/Jf93cO/4iGQ.BrxktP2.NUrYQOiwnDTEA9JDAqvkXS6', 1);


-- CREACIÓN DE CATEGORIAS
INSERT INTO categories (name, status) VALUES ('Electronica', 'ENABLED');
INSERT INTO categories (name, status) VALUES ('Ropa', 'ENABLED');
INSERT INTO categories (name, status) VALUES ('Deportes', 'ENABLED');
INSERT INTO categories (name, status) VALUES ('Hogar', 'ENABLED');


-- CREACIÓN DE PRODUCTOS
INSERT INTO products (name, price, status, category_id) VALUES ('Smartphone', 500.00, 'ENABLED', 1);
INSERT INTO products (name, price, status, category_id) VALUES ('Auriculares Bluetooth', 50.00, 'DISABLED', 1);
INSERT INTO products (name, price, status, category_id) VALUES ('Tablet', 300.00, 'ENABLED', 1);

INSERT INTO products (name, price, status, category_id) VALUES ('Camiseta', 25.00, 'ENABLED', 2);
INSERT INTO products (name, price, status, category_id) VALUES ('Pantalones', 35.00, 'ENABLED', 2);
INSERT INTO products (name, price, status, category_id) VALUES ('Zapatos', 45.00, 'ENABLED', 2);

INSERT INTO products (name, price, status, category_id) VALUES ('Balon de Futbol', 20.00, 'ENABLED', 3);
INSERT INTO products (name, price, status, category_id) VALUES ('Raqueta de Tenis', 80.00, 'DISABLED', 3);

INSERT INTO products (name, price, status, category_id) VALUES ('Aspiradora', 120.00, 'ENABLED', 4);
INSERT INTO products (name, price, status, category_id) VALUES ('Licuadora', 50.00, 'ENABLED', 4);

#TRUNCATE TABLE users;
#DROP TABLE users;
#SELECT *  FROM users;

SELECT * FROM roles;

SELECT * 
FROM Roles r
FULL OUTER JOIN 
