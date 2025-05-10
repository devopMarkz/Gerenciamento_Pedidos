package io.github.devopMarkz.inventory_service.dto;

public record ProductInStockDTO(
        Long id,
        String barcode,
        String name,
        Integer stockQuantity
) {
}
