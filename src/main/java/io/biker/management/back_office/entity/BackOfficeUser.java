package io.biker.management.back_office.entity;

import io.biker.management.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "BACK_OFFICE_USERS")
public class BackOfficeUser extends User {
    public BackOfficeUser() {
        super();
    }

    public BackOfficeUser(int id, String name, String email, String phoneNumber) {
        super(id, name, email, phoneNumber);
    }
}
