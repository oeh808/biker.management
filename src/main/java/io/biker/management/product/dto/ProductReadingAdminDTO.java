package io.biker.management.product.dto;

public record ProductReadingAdminDTO(int productId, String name, float price, int quantity, int storeId) {
}
