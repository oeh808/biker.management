package io.biker.management.auth.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.biker.management.auth.entity.UserRoles;

public interface UserRolesRepo extends MongoRepository<UserRoles, Integer> {
    public Optional<UserRoles> findByUser_Email(String email);

    public Optional<UserRoles> findByUser_PhoneNumber(String phoneNumber);
}
