package io.biker.management.user.entity;

import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// @Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class User {
    @Transient
    public static final String SEQUENCE_NAME = "usersSequence";

    @Id
    private int id;
    private String name;
    // FIXME: Add a way to ensure email is unique
    private String email;
    // FIXME: Add a way to ensure Phone Number is unique
    private String phoneNumber;
    private String password;
}
