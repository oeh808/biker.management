package io.biker.management.biker.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.biker.entity.Biker;

public interface BikerRepo extends JpaRepository<Biker, Integer> {
}
