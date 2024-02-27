package io.biker.management.customer.entity;

import java.util.HashSet;
import java.util.Set;

import io.biker.management.user.Address;
import io.biker.management.user.entity.User;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Customers")
public class Customer extends User {
    @Embedded
    private Set<Address> addresses = new HashSet<Address>();

    public Customer() {
        super();
    }

    public Customer(int id, String name, String email, String phoneNumber, Set<Address> addresses) {
        super(id, name, email, phoneNumber);
        this.addresses = addresses;
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
