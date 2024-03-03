package io.biker.management.backOffice.service;

import java.util.List;

import io.biker.management.backOffice.entity.BackOfficeUser;

public interface BackOfficeService {

    public BackOfficeUser createBackOfficeUser(BackOfficeUser boUser);

    public List<BackOfficeUser> getAllBackOfficeUsers();

    public BackOfficeUser getSingleBackOfficeUser(int id);

    public void deleteBackOfficeUser(int id);
}
