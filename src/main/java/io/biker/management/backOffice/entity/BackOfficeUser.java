package io.biker.management.backOffice.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import io.biker.management.user.entity.User;

@Document(collection = "BackOffice Users")
public class BackOfficeUser extends User {
    public BackOfficeUser() {
        super();
    }

    public BackOfficeUser(int id, String name, String email, String phoneNumber, String password) {
        super(id, name, email, phoneNumber, password);
    }
}
