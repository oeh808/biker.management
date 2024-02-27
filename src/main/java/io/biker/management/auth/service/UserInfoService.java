package io.biker.management.auth.service;

import io.biker.management.auth.entity.UserInfo;

public interface UserInfoService {
    public UserInfo addUser(UserInfo user);

    public void deleteUser(int id);

    public boolean isDuplicateUsername(String username);
}
