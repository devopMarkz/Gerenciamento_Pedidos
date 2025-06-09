package io.github.devopMarkz.cliente_service.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf", unique = true, nullable = true)
    private String cpf;

    @Column(name = "is_fidelizado", nullable = false)
    private Boolean isFidelizado = false;

    @Column(name = "quantidade_de_compras")
    private Integer quantidadeDeCompras = 0;

    public Cliente() {
    }

    public Cliente(Long id, String nome, String cpf, Boolean isFidelizado) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.isFidelizado = isFidelizado;
    }

    public void efetuarCompra() {
        this.quantidadeDeCompras++;
        if (this.quantidadeDeCompras >= 20) this.isFidelizado = true;
    }

    public void cancelarCompra() {
        this.quantidadeDeCompras--;
        if (this.quantidadeDeCompras < 20) this.isFidelizado = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Boolean getFidelizado() {
        return isFidelizado;
    }

    public void setFidelizado(Boolean fidelizado) {
        isFidelizado = fidelizado;
    }

    public Integer getQuantidadeDeCompras() {
        return quantidadeDeCompras;
    }

    public void setQuantidadeDeCompras(Integer quantidadeDeCompras) {
        this.quantidadeDeCompras = quantidadeDeCompras;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf);
    }
}
