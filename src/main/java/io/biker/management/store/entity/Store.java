package io.biker.management.store.entity;

import java.util.List;

import io.biker.management.product.entity.Product;
import io.biker.management.user.Address;
import io.biker.management.user.entity.User;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Stores")
public class Store extends User {
    @Embedded
    private Address address;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "store")
    private List<Product> products;

    public Store() {

    }

    public Store(int id, String name, String email, String phoneNumber, Address address, List<Product> products) {
        super(id, name, email, phoneNumber);
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
