package io.github.devopMarkz.pedido_service.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.devopMarkz.pedido_service.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDTO {

    private Long id;
    private Long clienteId;
    private StatusPedido status;
    private BigDecimal total;

    @JsonManagedReference
    private List<ItemPedidoDTO> itens = new ArrayList<>();

    public PedidoDTO() {
    }

    public PedidoDTO(Long id, Long clienteId, StatusPedido status, BigDecimal total) {
        this.id = id;
        this.clienteId = clienteId;
        this.status = status;
        this.total = total;
    }

    public PedidoDTO(Long id, Long clienteId, StatusPedido status, BigDecimal total, List<ItemPedidoDTO> itens) {
        this.id = id;
        this.clienteId = clienteId;
        this.status = status;
        this.total = total;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
}

