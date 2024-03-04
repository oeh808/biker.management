package io.biker.management.biker.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.biker.entity.Biker;

public interface BikerRepo extends JpaRepository<Biker, Integer> {
    Optional<Biker> findByEmail(String email);

    Optional<Biker> findByPhoneNumber(String phoneNumber);
}
