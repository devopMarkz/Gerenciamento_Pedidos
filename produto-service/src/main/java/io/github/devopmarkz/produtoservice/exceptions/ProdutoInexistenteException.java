package io.github.devopmarkz.produtoservice.exceptions;

public class ProdutoInexistenteException extends RuntimeException {
    public ProdutoInexistenteException(String message) {
        super(message);
    }
}
