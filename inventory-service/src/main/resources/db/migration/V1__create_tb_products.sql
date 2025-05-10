CREATE TABLE tb_products (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    barcode INT NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(100) NOT NULL,
    price DECIMAL(10,3) NOT NULL,
    stock_quantity INT NOT NULL,
    category VARCHAR(30) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);