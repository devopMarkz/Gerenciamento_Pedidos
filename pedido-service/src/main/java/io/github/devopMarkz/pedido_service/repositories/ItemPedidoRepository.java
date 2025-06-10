package io.github.devopMarkz.pedido_service.repositories;

import io.github.devopMarkz.pedido_service.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
