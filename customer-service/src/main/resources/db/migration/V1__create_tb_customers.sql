CREATE TABLE tb_customers(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150),
    email VARCHAR(100),
    phone_number VARCHAR(25),
    address VARCHAR(100),
    created_at TIMESTAMP
);