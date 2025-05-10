package io.github.devopMarkz.inventory_service.controllers;

import io.github.devopMarkz.inventory_service.dto.ProductInStockDTO;
import io.github.devopMarkz.inventory_service.dto.ProductResponseDTO;
import io.github.devopMarkz.inventory_service.dto.ProductUpdateQuantityDTO;
import io.github.devopMarkz.inventory_service.model.Product;
import io.github.devopMarkz.inventory_service.projections.ProductInStockProjection;
import io.github.devopMarkz.inventory_service.repositories.ProductRepository;
import io.github.devopMarkz.inventory_service.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final ProductService productService;

    public InventoryController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(
            @RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        Page<ProductResponseDTO> productResponseDTOS = productService.findAll(pageNumber, pageSize);
        return ResponseEntity.ok(productResponseDTOS);
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<ProductInStockDTO> getProductInStockById(@PathVariable("produtoId") Long id) {
        var productInStock = productService.findProductInStockById(id);
        return ResponseEntity.ok(productInStock);
    }

    @PostMapping("/{produtoId}/update")
    public ResponseEntity<String> updateQuantityInStock(
            @PathVariable("produtoId") Long id,
            @Valid @RequestBody ProductUpdateQuantityDTO productUpdateQuantityDTO
    ) {
        productService.updateProductInStock(id, productUpdateQuantityDTO);
        return ResponseEntity.ok("Product updated successfully!");
    }
}
