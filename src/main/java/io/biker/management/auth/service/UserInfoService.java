package io.biker.management.auth.service;

import io.biker.management.auth.entity.UserInfo;

public interface UserInfoService {
    public String addUser(UserInfo user);

    public String deleteUser(int id);

    public boolean isDuplicateUsername(String username);
}
