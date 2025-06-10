package io.github.devopMarkz.pedido_service.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_item_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column(name = "produto_id", nullable = false)
    private Long produtoId;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false)
    private BigDecimal preco;

    @Column(name = "total_item", nullable = false)
    private BigDecimal totalItem;

    public ItemPedido() {
    }

    public ItemPedido(Pedido pedido, Long produtoId, Integer quantidade, BigDecimal preco, BigDecimal totalItem) {
        this.pedido = pedido;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.preco = preco;
        this.totalItem = totalItem;
    }

    public ItemPedido(Long id, Pedido pedido, Long produtoId, Integer quantidade, BigDecimal preco, BigDecimal totalItem) {
        this.id = id;
        this.pedido = pedido;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.preco = preco;
        this.totalItem = totalItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        return Objects.equals(id, that.id) && Objects.equals(pedido, that.pedido) && Objects.equals(produtoId, that.produtoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pedido, produtoId);
    }
}
