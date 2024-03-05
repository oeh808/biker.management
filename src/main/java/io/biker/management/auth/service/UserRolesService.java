package io.biker.management.auth.service;

import io.biker.management.auth.entity.UserRoles;

public interface UserRolesService {
    public UserRoles addUser(UserRoles user);

    public void deleteUser(int id);
}
