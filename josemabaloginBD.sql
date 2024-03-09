CREATE DATABASE josemabalogin;
#DROP database josemabalogin;
USE josemabalogin;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    password VARCHAR(255),
    role ENUM(
		'ROLE_ADMISNISTRATOR', 
		'ROLE_ASSISTANT_ADMINISTRATOR', 
		'ROLE_CUSTOMER'
	) -- replace with your actual roles
);

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    status ENUM('ENABLED', 'DISABLED')
);

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price DECIMAL(19, 2),
    status ENUM('ENABLED', 'DISABLED'),
    category_id BIGINT
);

ALTER TABLE products ADD CONSTRAINT FK_products_categories FOREIGN KEY(category_id)
REFERENCES categories(id);


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

INSERT INTO users (username,name, password, role) VALUES ('jose', 'Jose Manuel', '$2a$10$BM1xGYNrqYNNzBd9LA7dB.qTh0we/UjYxFnKexfgO/yLZ67BPk3Qm', 'ROLE_ADMISNISTRATOR');
INSERT INTO users (username,name, password, role) VALUES ('rubi', 'Rubi Guadalupe', '$2a$10$K7C3gVm3s11G388M6HIAceIeutPuWhi20kebm.oTzWRsNNcrA1dVu', 'ROLE_ASSISTANT_ADMINISTRATOR');
INSERT INTO users (username,name, password, role) VALUES ('margarita', 'Margarita', '$2a$10$wfu/o5skr/Jf93cO/4iGQ.BrxktP2.NUrYQOiwnDTEA9JDAqvkXS6', 'ROLE_CUSTOMER');


SELECT *  FROM users;
