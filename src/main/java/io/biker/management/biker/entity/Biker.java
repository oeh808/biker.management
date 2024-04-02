package io.biker.management.biker.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import io.biker.management.user.Address;
import io.biker.management.user.entity.User;

@Document(collection = "Bikers")
public class Biker extends User {
    private Address currentLocation;

    public Biker() {
        super();
    }

    public Biker(int id, String name, String email, String phoneNumber, String password, Address currentLocation) {
        super(id, name, email, phoneNumber, password);
        this.currentLocation = currentLocation;
    }

    public Address getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Address currentLocation) {
        this.currentLocation = currentLocation;
    }
}
