package io.biker.management.product.service;

import java.util.List;

import io.biker.management.product.entity.Product;

public interface ProductService {
    public Product createProduct(Product product);

    public List<Product> getAllProducts();

    public Product getSingleProduct(int id);

    public Product updateProduct(int storeId, Product product);

    public void deleteProduct(int id);
}
