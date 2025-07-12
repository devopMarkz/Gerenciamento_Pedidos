package io.github.devopMarkz.pedido_service.integration;

import io.github.devopMarkz.pedido_service.dto.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "produto-service")
public interface ProdutoServiceClient {

    @GetMapping("/produtos/{id}")
    public ProdutoDTO findProdutoById(@PathVariable Long id);

    @PutMapping("/produtos/estoque/reducao")
    public void retirarProdutosDeEstoque(@RequestBody List<ProdutoDTO> produtos);

    @PutMapping("/produtos/estoque/reposicao")
    public void reporEstoque(@RequestBody List<ProdutoDTO> produtos);

}
