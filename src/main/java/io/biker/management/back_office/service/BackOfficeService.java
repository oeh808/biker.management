package io.biker.management.back_office.service;

import java.util.List;

import io.biker.management.back_office.entity.BackOfficeUser;

public interface BackOfficeService {

    public BackOfficeUser createBackOfficeUser(BackOfficeUser boUser);

    public List<BackOfficeUser> getAllBackOfficeUsers();

    public BackOfficeUser getSingleBackOfficeUser(int id);

    public void deleteBackOfficeUser(int id);
}
