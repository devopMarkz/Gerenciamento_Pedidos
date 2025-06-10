CREATE TABLE tb_item_pedido (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    total_item DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_pedido FOREIGN KEY (pedido_id) REFERENCES tb_pedidos(id) ON DELETE CASCADE
);