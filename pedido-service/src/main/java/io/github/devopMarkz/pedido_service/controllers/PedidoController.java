package io.github.devopMarkz.pedido_service.controllers;

import io.github.devopMarkz.pedido_service.dto.PedidoDTO;
import io.github.devopMarkz.pedido_service.model.Pedido;
import io.github.devopMarkz.pedido_service.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static io.github.devopMarkz.pedido_service.utils.GeneratorUri.generateUri;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin("*")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Void> criarPedido(@RequestBody Pedido pedido) {
        var pedidoSalvo = pedidoService.save(pedido);
        URI location = generateUri(pedidoSalvo.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> findAll(
            @RequestParam(value = "status", required = false) String status
    ) {
        return ResponseEntity.ok(pedidoService.getPedidos(status));
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Void> confirmarPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.confirmarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        PedidoDTO pedido = pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

}
