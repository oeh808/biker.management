package io.biker.management.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biker.management.auth.entity.UserInfo;

public interface UserInfoRepo extends JpaRepository<UserInfo, Integer> {
    public Optional<UserInfo> findByUser_Email(String email);

    public Optional<UserInfo> findByUser_PhoneNumber(String phoneNumber);
}
