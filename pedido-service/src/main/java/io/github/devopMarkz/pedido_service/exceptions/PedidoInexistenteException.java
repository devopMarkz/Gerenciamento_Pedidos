package io.github.devopMarkz.pedido_service.exceptions;

public class PedidoInexistenteException extends RuntimeException {
    public PedidoInexistenteException(String message) {
        super(message);
    }
}
