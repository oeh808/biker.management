package io.biker.management.biker.entity;

import io.biker.management.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "BIKERS")
public class Biker extends User {
    private String currentLocation;

    public Biker() {
        super();
    }

    public Biker(int id, String name, String email, String phoneNumber, String currentLocation) {
        super(id, name, email, phoneNumber);
        this.currentLocation = currentLocation;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }
}
