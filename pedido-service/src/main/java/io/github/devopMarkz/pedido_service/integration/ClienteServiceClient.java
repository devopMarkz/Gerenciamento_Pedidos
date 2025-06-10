package io.github.devopMarkz.pedido_service.integration;

import io.github.devopMarkz.pedido_service.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-service", url = "http://localhost:8081")
public interface ClienteServiceClient {

    @GetMapping("/clientes/{id}")
    public ClienteDTO findClienteById(@PathVariable Long id);

    @GetMapping("/clientes/{cpf}")
    public ClienteDTO findClienteByCpf(@PathVariable String cpf);

}
