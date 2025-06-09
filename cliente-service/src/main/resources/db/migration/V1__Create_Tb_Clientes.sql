CREATE TABLE tb_clientes(
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150),
    cpf VARCHAR(20) UNIQUE,
    is_fidelizado BOOLEAN,
    quantidade_de_compras INT
);