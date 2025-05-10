package io.github.devopMarkz.inventory_service.repositories;

import io.github.devopMarkz.inventory_service.model.Product;
import io.github.devopMarkz.inventory_service.projections.ProductInStockProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
            value = "SELECT id, barcode, name, stock_quantity FROM tb_products WHERE id = :id",
            nativeQuery = true
    )
    ProductInStockProjection getProductInStockById(@Param("id") Long id);

}
