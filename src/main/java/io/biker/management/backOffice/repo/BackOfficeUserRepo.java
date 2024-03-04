package io.biker.management.backOffice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.backOffice.entity.BackOfficeUser;

public interface BackOfficeUserRepo extends JpaRepository<BackOfficeUser, Integer> {
    Optional<BackOfficeUser> findByEmail(String email);

    Optional<BackOfficeUser> findByPhoneNumber(String phoneNumber);
}
