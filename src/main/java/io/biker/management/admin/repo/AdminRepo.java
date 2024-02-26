package io.biker.management.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.admin.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer> {

}
