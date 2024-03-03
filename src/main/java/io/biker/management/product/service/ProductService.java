package io.biker.management.product.service;

import java.util.List;

import io.biker.management.product.entity.Product;

public interface ProductService {
    public Product createProduct(int storeId, Product product);

    public List<Product> getAllProducts();

    public List<Product> getAllProductsByStore(int storeId);

    public Product getSingleProduct(int storeId, int id);

    public Product getProduct(int id);

    public Product updateProduct(int storeId, Product product);

    public void deleteProduct(int storeId, int id);
}
