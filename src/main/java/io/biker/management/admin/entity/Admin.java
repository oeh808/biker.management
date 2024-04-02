package io.biker.management.admin.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import io.biker.management.user.entity.User;

@Document(collection = "Admins")
public class Admin extends User {
    public Admin() {
        super();
    }

    public Admin(int id, String name, String email, String phoneNumber, String password) {
        super(id, name, email, phoneNumber, password);
    }
}
