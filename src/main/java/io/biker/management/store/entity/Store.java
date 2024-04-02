package io.biker.management.store.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import io.biker.management.product.entity.Product;
import io.biker.management.user.Address;
import io.biker.management.user.entity.User;

@Document(collection = "Stores")
public class Store extends User {
    private Address address;
    private List<Product> products;

    public Store() {
        products = new ArrayList<>();
    }

    public Store(int id, String name, String email, String phoneNumber, String password, Address address,
            List<Product> products) {
        super(id, name, email, phoneNumber, password);
        this.address = address;
        this.products = products;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}
