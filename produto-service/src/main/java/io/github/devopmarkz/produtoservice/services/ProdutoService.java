package io.github.devopmarkz.produtoservice.services;

import io.github.devopmarkz.produtoservice.exceptions.EstoqueInsuficienteException;
import io.github.devopmarkz.produtoservice.exceptions.ProdutoInexistenteException;
import io.github.devopmarkz.produtoservice.model.Produto;
import io.github.devopmarkz.produtoservice.repositories.ProdutoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<Produto> findByFilter(Long id, String nome, String descricao) {
        Produto produto = new Produto(id, nome, descricao, null);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Produto> example = Example.of(produto, matcher);

        return produtoRepository.findAll(example);
    }

    @Transactional(readOnly = true)
    public Produto findById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoInexistenteException("Produto Inexistente."));
    }

    @Transactional
    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Transactional
    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Produto produto) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoInexistenteException("Produto Inexistente."));

        produtoExistente.setNome(produto.getNome() == null? produtoExistente.getNome() : produto.getNome());
        produtoExistente.setDescricao(produto.getDescricao() == null? produtoExistente.getDescricao() : produto.getDescricao());
        produtoExistente.setPreco(produto.getPreco() == null? produtoExistente.getPreco() : produto.getPreco());
    }

    @Transactional
    public void retirarProdutoDeEstoque(List<Produto> produtos) {
        List<String> erros = new ArrayList<>();

        Map<Long, Produto> produtosSalvos = new HashMap<>();

            for (Produto produto : produtos) {
                try{
                    Produto produtoSalvo = produtoRepository.findById(produto.getId())
                            .orElseThrow(() -> new ProdutoInexistenteException(
                                    "Produto com ID " + produto.getId() + " inexistente."));

                    if (!produtoSalvo.isDisponivel(produto.getQuantidade())) {
                        throw new EstoqueInsuficienteException(
                                "Estoque insuficiente para o produto " + produto.getId() + ". Quantidade solicitada: "
                                        + produto.getQuantidade() + ", disponível: " + produtoSalvo.getQuantidade());
                    }

                    produtosSalvos.put(produto.getId(), produtoSalvo);
                } catch (RuntimeException e) {
                    erros.add(e.getMessage());
                }
            }

        if (!erros.isEmpty()) {
            EstoqueInsuficienteException ex = new EstoqueInsuficienteException("Estoque insuficiente para os produtos listados.");
            ex.setErros(erros);
            throw ex;
        }

        for (Produto produto : produtos) {
            Produto produtoSalvo = produtosSalvos.get(produto.getId());
            produtoSalvo.removerQuantidadeEmEstoque(produto.getQuantidade());
        }
    }

    @Transactional
    public void reporEstoque(List<Produto> produtos) {
        for (Produto produto : produtos) {
            Produto produtoSalvo = produtoRepository.findById(produto.getId())
                    .orElseThrow(() -> new ProdutoInexistenteException("Produto Inexistente."));

            produtoSalvo.adicionarQuantidade(produto.getQuantidade());
        }
    }

}
