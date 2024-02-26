package io.biker.management.admin.entity;

import io.biker.management.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ADMINS")
public class Admin extends User {
    public Admin() {
        super();
    }

    public Admin(int id, String name, String email, String phoneNumber) {
        super(id, name, email, phoneNumber);
    }
}
