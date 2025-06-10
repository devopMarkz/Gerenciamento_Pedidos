package io.github.devopMarkz.pedido_service.integration;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "produto-service", url = "http://localhost:8080")
public interface ProdutoServiceClient {



}
