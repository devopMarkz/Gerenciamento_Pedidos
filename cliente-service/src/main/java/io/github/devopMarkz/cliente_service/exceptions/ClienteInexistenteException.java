package io.github.devopMarkz.cliente_service.exceptions;

public class ClienteInexistenteException extends RuntimeException {
    public ClienteInexistenteException(String message) {
        super(message);
    }
}
