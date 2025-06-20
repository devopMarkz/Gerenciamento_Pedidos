package io.github.devopMarkz.pedido_service.repositories;

import io.github.devopMarkz.pedido_service.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query(value = "SELECT * FROM tb_pedidos WHERE tb_pedidos.status = :status", nativeQuery = true)
    List<Pedido> buscarPedidoPorStatus(@Param("status") String status);

}
