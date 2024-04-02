package io.biker.management.backOffice.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.biker.management.backOffice.entity.BackOfficeUser;

public interface BackOfficeUserRepo extends MongoRepository<BackOfficeUser, Integer> {
    Optional<BackOfficeUser> findByEmail(String email);

    Optional<BackOfficeUser> findByPhoneNumber(String phoneNumber);
}
