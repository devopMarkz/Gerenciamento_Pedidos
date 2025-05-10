package io.github.devopMarkz.inventory_service.util;

import io.github.devopMarkz.inventory_service.dto.ProductInStockDTO;
import io.github.devopMarkz.inventory_service.dto.ProductResponseDTO;
import io.github.devopMarkz.inventory_service.model.Product;
import io.github.devopMarkz.inventory_service.projections.ProductInStockProjection;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductInStockDTO toProductInStockDTO(ProductInStockProjection productInStockProjection) {
        return new ProductInStockDTO(
                productInStockProjection.getId(),
                productInStockProjection.getBarcode(),
                productInStockProjection.getName(),
                productInStockProjection.getStockQuantity()
        );
    }

    public ProductResponseDTO productToProductResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getBarcode(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory()
        );
    }

}
