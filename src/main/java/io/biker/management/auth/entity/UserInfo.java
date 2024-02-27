package io.biker.management.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @Id
    private int id;
    @Column(unique = true)
    private String username;
    // FIXME: Add some form of verification for phone numbers
    private String password;
    @Column(unique = true)
    private String phoneNumber;
    private String roles;
}
