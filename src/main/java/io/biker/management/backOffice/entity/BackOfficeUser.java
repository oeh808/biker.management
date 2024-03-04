package io.biker.management.backOffice.entity;

import io.biker.management.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "BACK_OFFICE_USERS")
public class BackOfficeUser extends User {
    public BackOfficeUser() {
        super();
    }

    public BackOfficeUser(int id, String name, String email, String phoneNumber, String password) {
        super(id, name, email, phoneNumber, password);
    }
}
