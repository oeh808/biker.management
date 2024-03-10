package io.biker.management.auth.service;

import io.biker.management.auth.entity.UserRoles;

public interface UserRolesService {
    public UserRoles addUser(UserRoles user);

    public UserRoles registerCustomer(int id);

    public UserRoles registerBiker(int id);

    public UserRoles registerStore(int id);

    public UserRoles registerBackOfficeUser(int id);

    public void deleteUser(int id);
}
