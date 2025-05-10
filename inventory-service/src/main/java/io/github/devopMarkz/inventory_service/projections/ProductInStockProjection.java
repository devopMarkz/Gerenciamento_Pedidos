package io.github.devopMarkz.inventory_service.projections;

public interface ProductInStockProjection {
    Long getId();
    String getBarcode();
    String getName();
    Integer getStockQuantity();
}
