package io.biker.management.backOffice.entity;

import io.biker.management.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "BACK_OFFICE_USERS")
public class BackOfficeUser extends User {
}
