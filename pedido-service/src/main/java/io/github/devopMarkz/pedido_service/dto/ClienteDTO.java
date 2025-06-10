package io.github.devopMarkz.pedido_service.dto;

public class ClienteDTO {

    private Long id;
    private String nome;
    private String cpf;
    private Boolean isFidelizado;
    private Integer quantidadeDeCompras;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String nome, String cpf, Boolean isFidelizado, Integer quantidadeDeCompras) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.isFidelizado = isFidelizado;
        this.quantidadeDeCompras = quantidadeDeCompras;
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
}
