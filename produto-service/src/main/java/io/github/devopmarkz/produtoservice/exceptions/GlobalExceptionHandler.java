package io.github.devopmarkz.produtoservice.exceptions;

import io.github.devopmarkz.produtoservice.dto.ErroDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<ErroDTO> handleEstoqueInsuficienteException(EstoqueInsuficienteException ex) {
        ErroDTO erroDTO = new ErroDTO(ex.getMessage(), 400, Instant.now());
        erroDTO.setErrors(ex.getErros());
        return ResponseEntity.status(400).body(erroDTO);
    }

}
