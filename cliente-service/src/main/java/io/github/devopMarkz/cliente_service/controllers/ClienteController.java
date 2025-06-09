package io.github.devopMarkz.cliente_service.controllers;

import io.github.devopMarkz.cliente_service.model.Cliente;
import io.github.devopMarkz.cliente_service.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static io.github.devopMarkz.cliente_service.utils.UriGenerator.generateUri;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        Cliente result = clienteService.save(cliente);
        URI location = generateUri(result.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> findByFilters(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "cpf", required = false) String cpf,
            @RequestParam(name = "isFidelizado", required = false) boolean isFidelizado
    ) {
        List<Cliente> clientes = clienteService.findByFilters(id, nome, cpf, isFidelizado);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> findByCpf(@PathVariable String cpf) {
        Cliente cliente = clienteService.findByCpf(cpf);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Cliente cliente) {
        clienteService.update(id, cliente);
        return ResponseEntity.noContent().build();
    }

}
