package io.github.devopMarkz.pedido_service.services;

import io.github.devopMarkz.pedido_service.dto.ProdutoDTO;
import io.github.devopMarkz.pedido_service.exceptions.ProdutoNaoEncontradoException;
import io.github.devopMarkz.pedido_service.integration.ProdutoServiceClient;
import io.github.devopMarkz.pedido_service.model.ItemPedido;
import io.github.devopMarkz.pedido_service.model.Pedido;
import io.github.devopMarkz.pedido_service.repositories.ItemPedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;
    private final ProdutoServiceClient produtoServiceClient;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository, ProdutoServiceClient produtoServiceClient) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.produtoServiceClient = produtoServiceClient;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemPedido> saveAll(List<ItemPedido> itemPedidos, Pedido pedido) {
        List<ProdutoDTO> produtoDTOS = new ArrayList<>();

        try {
            for (ItemPedido itemPedido : itemPedidos) {
                ProdutoDTO produtoDTO = produtoServiceClient.findProdutoById(itemPedido.getProdutoId());

                if (produtoDTO == null) {
                    throw new ProdutoNaoEncontradoException("Produto com ID " + itemPedido.getProdutoId() + " não encontrado.");
                }

                produtoDTO.setQuantidade(itemPedido.getQuantidade());

                produtoDTOS.add(produtoDTO);

                itemPedido.setPreco(produtoDTO.getPreco());
                itemPedido.setProdutoId(produtoDTO.getId());
                itemPedido.calcularTotalItem();

                itemPedido.setPedido(pedido);
            }

            produtoServiceClient.retirarProdutosDeEstoque(produtoDTOS);

            return itemPedidoRepository.saveAll(itemPedidos);
        } catch (Exception e) {
            produtoServiceClient.reporEstoque(produtoDTOS);
            throw new RuntimeException("Algum erro.");
        }
    }

}
