package io.biker.management.back_office.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.back_office.entity.BackOfficeUser;

public interface BackOfficeUserRepo extends JpaRepository<BackOfficeUser, Integer> {
}
