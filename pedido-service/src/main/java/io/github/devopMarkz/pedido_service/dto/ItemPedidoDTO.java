package io.github.devopMarkz.pedido_service.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;

public class ItemPedidoDTO {

    private Long id;
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal preco;
    private BigDecimal totalItem;

    @JsonBackReference
    private PedidoDTO pedido;

    public ItemPedidoDTO() {
    }

    public ItemPedidoDTO(Long id, Long produtoId, Integer quantidade, BigDecimal preco, BigDecimal totalItem, PedidoDTO pedido) {
        this.id = id;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.preco = preco;
        this.totalItem = totalItem;
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(BigDecimal totalItem) {
        this.totalItem = totalItem;
    }

    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
    }
}