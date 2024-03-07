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
import lombok.extern.log4j.Log4j2;

@Log4j2
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
        log.info("Running createProduct(" + storeId + "," + product.toString() + ") in ProductServiceImpl...");
        Store store = getStore(storeId);
        product.setStore(store);

        log.info("Saving Product...");
        return productRepo.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Running getAllProducts() in ProductServiceImpl...");
        return productRepo.findAll();
    }

    @Override
    public List<Product> getAllProductsByStore(int storeId) {
        log.info("Running getAllProductsByStore(" + storeId + ") in ProductServiceImpl...");
        Store store = getStore(storeId);
        return productRepo.findByStore(store);
    }

    @Override
    public Product getSingleProduct(int storeId, int id) {
        log.info("Running getSingleProduct(" + storeId + "," + id + ") in ProductServiceImpl...");
        Store store = getStore(storeId);
        Optional<Product> opProduct = productRepo.findByProductIdAndStore(id, store);
        if (opProduct.isPresent()) {
            return opProduct.get();
        } else {
            log.error("Invalid combination of product id: " + id + " and store id: " + storeId + "!");
            throw new ProductException(ProductExceptionMessages.PRODUCT_NOT_FOUND(storeId));
        }
    }

    @Override
    public Product getProduct(int id) {
        log.info("Running getProduct(" + id + ") in ProductServiceImpl...");
        Optional<Product> opProduct = productRepo.findById(id);
        if (opProduct.isPresent()) {
            return opProduct.get();
        } else {
            log.error("Invalid product id: " + id + "!");
            throw new ProductException(ProductExceptionMessages.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public Product updateProduct(int storeId, Product product) {
        log.info("Running updateProduct(" + storeId + "," + product.toString() + ") in ProductServiceImpl...");
        Product ogProduct = getSingleProduct(storeId, product.getProductId());
        product.setStore(ogProduct.getStore());

        log.info("Saving updated product...");
        return productRepo.save(product);
    }

    @Override
    public void deleteProduct(int storeId, int id) {
        log.info("Running deleteProduct(" + storeId + "," + id + ") in ProductServiceImpl...");
        getSingleProduct(storeId, id);

        log.info("Deleting product...");
        productRepo.deleteById(id);
    }

    // Helper function(s)
    private Store getStore(int storeId) {
        log.info("Running getStore(" + storeId + ") in ProductServiceImpl...");
        Optional<Store> opStore = storeRepo.findById(storeId);
        if (opStore.isPresent()) {
            return opStore.get();
        } else {
            log.error("Invalid store id: " + storeId + "!");
            throw new StoreException(StoreExceptionMessages.STORE_NOT_FOUND);
        }
    }
}
