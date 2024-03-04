package io.biker.management.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.auth.entity.UserRoles;

public interface UserRolesRepo extends JpaRepository<UserRoles, Integer> {
    public Optional<UserRoles> findByUser_Email(String email);

    public Optional<UserRoles> findByUser_PhoneNumber(String phoneNumber);
}
