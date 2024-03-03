package io.biker.management.backOffice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.backOffice.entity.BackOfficeUser;

public interface BackOfficeUserRepo extends JpaRepository<BackOfficeUser, Integer> {
}
