package io.biker.management.customer.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import io.biker.management.user.Address;
import io.biker.management.user.entity.User;

@Document(collection = "Customers")
public class Customer extends User {
    private Set<Address> addresses = new HashSet<Address>();

    public Customer() {
        super();
    }

    public Customer(int id, String name, String email, String phoneNumber, String password) {
        super(id, name, email, phoneNumber, password);
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
}
