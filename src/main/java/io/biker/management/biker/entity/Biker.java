package io.biker.management.biker.entity;

import io.biker.management.user.Address;
import io.biker.management.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "BIKERS")
public class Biker extends User {
    private Address currentLocation;

    public Biker() {
        super();
    }

    public Biker(int id, String name, String email, String phoneNumber, Address currentLocation) {
        super(id, name, email, phoneNumber);
        this.currentLocation = currentLocation;
    }

    public Address getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Address currentLocation) {
        this.currentLocation = currentLocation;
    }
}
