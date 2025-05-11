-- V2__populate_tb_customers.sql

INSERT INTO tb_customers (name, email, phone_number, address, created_at) VALUES
                                                                              ('João Silva', 'joao.silva@example.com', '1234567890', 'Rua das Flores, 123, São Paulo, SP', NOW()),
                                                                              ('Maria Oliveira', 'maria.oliveira@example.com', '0987654321', 'Av. Paulista, 456, São Paulo, SP', NOW()),
                                                                              ('Carlos Souza', 'carlos.souza@example.com', '1122334455', 'Rua da Liberdade, 789, São Paulo, SP', NOW()),
                                                                              ('Ana Costa', 'ana.costa@example.com', '9988776655', 'Rua dos Três Corações, 234, Rio de Janeiro, RJ', NOW()),
                                                                              ('Pedro Almeida', 'pedro.almeida@example.com', '6677889900', 'Rua da Paz, 1001, Rio de Janeiro, RJ', NOW());
