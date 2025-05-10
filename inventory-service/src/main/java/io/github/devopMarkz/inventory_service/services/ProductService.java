package io.github.devopMarkz.inventory_service.services;

import io.github.devopMarkz.inventory_service.dto.ProductInStockDTO;
import io.github.devopMarkz.inventory_service.dto.ProductResponseDTO;
import io.github.devopMarkz.inventory_service.dto.ProductUpdateQuantityDTO;
import io.github.devopMarkz.inventory_service.model.Product;
import io.github.devopMarkz.inventory_service.projections.ProductInStockProjection;
import io.github.devopMarkz.inventory_service.repositories.ProductRepository;
import io.github.devopMarkz.inventory_service.util.ProductMapper;
import jakarta.ws.rs.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::productToProductResponseDTO);
    }

    @Transactional(readOnly = true)
    public ProductInStockDTO findProductInStockById(Long id) {
        ProductInStockProjection productInStockProjection = productRepository.getProductInStockById(id);
        return productMapper.toProductInStockDTO(productInStockProjection);
    }

    @Transactional
    public void updateProductInStock(Long id, ProductUpdateQuantityDTO productUpdateQuantityDTO) {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Product not found!"));
        Integer quantidadeAtualizada = product.getStockQuantity() - productUpdateQuantityDTO.quantity();
        product.setStockQuantity(quantidadeAtualizada);
        productRepository.save(product);
    }
}
