package io.biker.management.admin.entity;

import io.biker.management.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "ADMINS")
public class Admin extends User {
}
