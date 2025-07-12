package io.github.devopMarkz.pedido_service.integration;

import io.github.devopMarkz.pedido_service.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "cliente-service")
public interface ClienteServiceClient {

    @GetMapping("/clientes/{id}")
    public ClienteDTO findClienteById(@PathVariable Long id);

    @GetMapping("/clientes/{cpf}")
    public ClienteDTO findClienteByCpf(@PathVariable String cpf);

    @PutMapping("/clientes/{id}/compra")
    void efetuarCompra(@PathVariable Long id);

    @PutMapping("/clientes/{id}/cancelamento-compra")
    void cancelaCompra(@PathVariable Long id);

}
