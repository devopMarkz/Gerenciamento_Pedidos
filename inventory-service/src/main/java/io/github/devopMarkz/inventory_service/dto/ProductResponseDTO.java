package io.github.devopMarkz.inventory_service.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String barcode,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String category
) {
}
