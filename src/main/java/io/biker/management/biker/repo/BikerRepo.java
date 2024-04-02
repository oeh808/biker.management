package io.biker.management.biker.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.biker.management.biker.entity.Biker;

public interface BikerRepo extends MongoRepository<Biker, Integer> {
    Optional<Biker> findByEmail(String email);

    Optional<Biker> findByPhoneNumber(String phoneNumber);
}
