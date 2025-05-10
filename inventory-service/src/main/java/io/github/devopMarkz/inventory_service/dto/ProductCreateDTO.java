package io.github.devopMarkz.inventory_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductCreateDTO(
        @NotBlank(message = "Produto deve conter código de barra.")
        @Pattern(regexp = "^[0-9]+$", message = "Somente números são permitidos")
        String barcode,

        @NotBlank(message = "Produto deve conter nome.")
        String name,

        String description,

        @PositiveOrZero(message = "Produto deve ter valor.")
        BigDecimal price,

        @PositiveOrZero(message = "Quantidade de produtos em estoque não pode ser negativa.")
        Integer stockQuantity,

        @NotBlank(message = "Produto deve ter categoria.")
        String category
) {
}
