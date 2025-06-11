package io.github.devopmarkz.produtoservice.controllers;

import io.github.devopmarkz.produtoservice.model.Produto;
import io.github.devopmarkz.produtoservice.services.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin("*")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> getProdutos(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "descricao", required = false) String descricao
    ) {
        List<Produto> produtos = produtoService.findByFilter(id, nome, descricao);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProduto(@PathVariable Long id) {
        Produto produto = produtoService.findById(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<Void> createProduto(@RequestBody Produto produto) {
        Produto produtoEncontrado = produtoService.save(produto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produtoEncontrado.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        produtoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Produto produto) {
        produtoService.update(id, produto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/estoque/reducao")
    public ResponseEntity<Void> retirarProdutosDeEstoque(@RequestBody List<Produto> produtos) {
        produtoService.retirarProdutoDeEstoque(produtos);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/estoque/reposicao")
    public ResponseEntity<Void> reporEstoque(@RequestBody List<Produto> produtos) {
        produtoService.reporEstoque(produtos);
        return ResponseEntity.noContent().build();
    }

}
