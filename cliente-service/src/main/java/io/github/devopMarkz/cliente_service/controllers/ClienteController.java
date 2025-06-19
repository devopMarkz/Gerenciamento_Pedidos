package io.github.devopMarkz.cliente_service.controllers;

import io.github.devopMarkz.cliente_service.model.Cliente;
import io.github.devopMarkz.cliente_service.services.ClienteService;
import io.github.devopMarkz.cliente_service.services.PlanilhaService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static io.github.devopMarkz.cliente_service.utils.UriGenerator.generateUri;

@RestController
@RequestMapping("/clientes")
@CrossOrigin("*")
public class ClienteController {

    private final ClienteService clienteService;
    private final PlanilhaService planilhaService;

    public ClienteController(ClienteService clienteService, PlanilhaService planilhaService) {
        this.clienteService = clienteService;
        this.planilhaService = planilhaService;
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
        return ResponseEntity.ok(clientes.stream().sorted().toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("cpf//{cpf}")
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

    @PutMapping("/{id}/compra")
    public ResponseEntity<Void> efetuaCompra(@PathVariable Long id) {
        clienteService.efetuarCompra(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/cancelamento-compra")
    public ResponseEntity<Void> cancelarCompra(@PathVariable Long id) {
        clienteService.cancelarCompra(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download/excel")
    public ResponseEntity<byte[]> downloadExcel(){
        byte[] resource = planilhaService.gerarPlanilha();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename("relatorio_clientes.xlsx").build());
        headers.setContentLength(resource.length);

        return ResponseEntity.ok().headers(headers).body(resource);
    }

}
