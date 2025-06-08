package io.github.devopmarkz.produtoservice.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ErroDTO {

    private String message;
    private Integer status;
    private Instant timestamp;
    private List<String> errors = new ArrayList<>();

    public ErroDTO(String message, Integer status, Instant timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
