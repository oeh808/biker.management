package io.biker.management.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.biker.management.product.entity.Product;
import io.biker.management.product.exception.ProductException;
import io.biker.management.product.exception.ProductExceptionMessages;
import io.biker.management.product.repo.ProductRepo;
import io.biker.management.store.entity.Store;
import io.biker.management.store.exception.StoreException;
import io.biker.management.store.exception.StoreExceptionMessages;
import io.biker.management.store.repo.StoreRepo;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepo productRepo;
    private StoreRepo storeRepo;

    public ProductServiceImpl(ProductRepo productRepo, StoreRepo storeRepo) {
        this.productRepo = productRepo;
        this.storeRepo = storeRepo;
    }

    @Override
    public Product createProduct(int storeId, Product product) {
        Store store = getStore(storeId);
        product.setStore(store);

        return productRepo.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getAllProductsByStore(int storeId) {
        Store store = getStore(storeId);
        return productRepo.findByStore(store);
    }

    @Override
    public Product getSingleProduct(int storeId, int id) {
        Store store = getStore(storeId);
        Optional<Product> opProduct = productRepo.findByProductIdAndStore(id, store);
        if (opProduct.isPresent()) {
            return opProduct.get();
        } else {
            throw new ProductException(ProductExceptionMessages.PRODUCT_NOT_FOUND(storeId));
        }
    }

    @Override
    public Product getProduct(int id) {
        Optional<Product> opProduct = productRepo.findById(id);
        if (opProduct.isPresent()) {
            return opProduct.get();
        } else {
            throw new ProductException(ProductExceptionMessages.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public Product updateProduct(int storeId, Product product) {
        Product ogProduct = getSingleProduct(storeId, product.getProductId());
        product.setStore(ogProduct.getStore());

        return productRepo.save(product);
    }

    @Override
    public void deleteProduct(int storeId, int id) {
        getSingleProduct(storeId, id);

        productRepo.deleteById(id);
    }

    // Helper function(s)
    private Store getStore(int storeId) {
        Optional<Store> opStore = storeRepo.findById(storeId);
        if (opStore.isPresent()) {
            return opStore.get();
        } else {
            throw new StoreException(StoreExceptionMessages.STORE_NOT_FOUND);
        }
    }
}
