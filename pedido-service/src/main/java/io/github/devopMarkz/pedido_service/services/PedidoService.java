package io.github.devopMarkz.pedido_service.services;

import io.github.devopMarkz.pedido_service.dto.ClienteDTO;
import io.github.devopMarkz.pedido_service.dto.PedidoDTO;
import io.github.devopMarkz.pedido_service.dto.ProdutoDTO;
import io.github.devopMarkz.pedido_service.exceptions.ClienteNaoEncontradoException;
import io.github.devopMarkz.pedido_service.exceptions.PedidoInexistenteException;
import io.github.devopMarkz.pedido_service.integration.ClienteServiceClient;
import io.github.devopMarkz.pedido_service.integration.ProdutoServiceClient;
import io.github.devopMarkz.pedido_service.model.ItemPedido;
import io.github.devopMarkz.pedido_service.model.Pedido;
import io.github.devopMarkz.pedido_service.model.enums.StatusPedido;
import io.github.devopMarkz.pedido_service.repositories.ItemPedidoRepository;
import io.github.devopMarkz.pedido_service.repositories.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ItemPedidoService itemPedidoService;
    private final ClienteServiceClient clienteServiceClient;
    private final ProdutoServiceClient produtoServiceClient;
    private final ModelMapper modelMapper;

    public PedidoService(PedidoRepository pedidoRepository, ItemPedidoRepository itemPedidoRepository, ItemPedidoService itemPedidoService, ClienteServiceClient clienteServiceClient, ProdutoServiceClient produtoServiceClient, ModelMapper modelMapper) {
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.itemPedidoService = itemPedidoService;
        this.clienteServiceClient = clienteServiceClient;
        this.produtoServiceClient = produtoServiceClient;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public Pedido getPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoInexistenteException("Pedido inexistente!"));
    }

    @Transactional(readOnly = true)
    public List<PedidoDTO> getPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoDTO> pedidoDTOS = pedidos.stream().map(p -> modelMapper.map(p, PedidoDTO.class)).toList();
        return pedidoDTOS;
    }

    @Transactional
    public Pedido save(Pedido pedido) {
        ClienteDTO clienteDTO = validarClienteExistente(pedido.getClienteId());

        Pedido novoPedido = criarPedido(clienteDTO);

        novoPedido = pedidoRepository.save(novoPedido);

        List<ItemPedido> itensPedido = itemPedidoService.saveAll(pedido.getItens(), novoPedido);

        BigDecimal total = calcularTotal(itensPedido);

        novoPedido.setItens(itensPedido);
        novoPedido.setTotal(total);

        return pedidoRepository.save(novoPedido);
    }

    private ClienteDTO validarClienteExistente(Long clienteId) {
        ClienteDTO clienteDTO = clienteServiceClient.findClienteById(clienteId);
        if (clienteDTO == null) {
            throw new ClienteNaoEncontradoException("Cliente com ID " + clienteId + " não encontrado.");
        }
        return clienteDTO;
    }

    private Pedido criarPedido(ClienteDTO clienteDTO) {
        return new Pedido(clienteDTO.getId(), StatusPedido.PENDENTE);
    }

    private BigDecimal calcularTotal(List<ItemPedido> itensPedido) {
        return itensPedido.stream()
                .map(ItemPedido::getTotalItem)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public Pedido confirmarPedido(Long idPedido){
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoInexistenteException("Pedido inexistente!"));

        pedido.setStatus(StatusPedido.CONCLUIDO);

        return pedido;
    }

    @Transactional
    public PedidoDTO cancelarPedido(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoInexistenteException("Pedido inexistente!"));

        pedido.setStatus(StatusPedido.CANCELADO);

        List<ProdutoDTO> produtoDTOS = pedido.getItens().stream().map(itemPedido -> produtoServiceClient.findProdutoById(itemPedido.getId())).toList();

        produtoServiceClient.reporEstoque(produtoDTOS);

        return modelMapper.map(pedido, PedidoDTO.class);
    }

}
