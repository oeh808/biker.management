package io.biker.management.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.biker.management.product.entity.Product;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Product createProduct(int storeId, Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProduct'");
    }

    @Override
    public List<Product> getAllProducts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllProducts'");
    }

    @Override
    public List<Product> getAllProductsByStore(int storeId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllProductsByStore'");
    }

    @Override
    public Product getSingleProduct(int storeId, int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSingleProduct'");
    }

    @Override
    public Product updateProduct(int storeId, Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProduct'");
    }

    @Override
    public void deleteProduct(int storeId, int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
    }

}
