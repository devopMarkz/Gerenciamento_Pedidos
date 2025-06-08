package io.github.devopmarkz.produtoservice.exceptions;

import java.util.ArrayList;
import java.util.List;

public class EstoqueInsuficienteException extends RuntimeException {

    List<String> erros = new ArrayList<>();

    public EstoqueInsuficienteException(String message) {
        super(message);
    }

    public List<String> getErros() {
        return erros;
    }

    public void setErros(List<String> erros) {
        this.erros = erros;
    }
}
