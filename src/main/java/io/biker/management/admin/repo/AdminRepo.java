package io.biker.management.admin.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.biker.management.admin.entity.Admin;

public interface AdminRepo extends MongoRepository<Admin, Integer> {

}
