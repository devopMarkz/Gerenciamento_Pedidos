package io.github.devopMarkz.inventory_service.dto;

import jakarta.validation.constraints.PositiveOrZero;

public record ProductUpdateQuantityDTO(
        @PositiveOrZero(message = "Quantidade requisitada deve ser positiva.")
        Integer quantity
) {
}
