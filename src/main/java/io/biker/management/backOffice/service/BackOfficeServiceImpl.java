package io.biker.management.backOffice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.backOffice.exception.BackOfficeException;
import io.biker.management.backOffice.exception.BackOfficeExceptionMessages;
import io.biker.management.backOffice.repo.BackOfficeUserRepo;

@Service
public class BackOfficeServiceImpl implements BackOfficeService {
    private BackOfficeUserRepo boRepo;

    public BackOfficeServiceImpl(BackOfficeUserRepo boRepo) {
        this.boRepo = boRepo;
    }

    @Override
    public BackOfficeUser createBackOfficeUser(BackOfficeUser boUser) {
        return boRepo.save(boUser);
    }

    @Override
    public List<BackOfficeUser> getAllBackOfficeUsers() {
        return boRepo.findAll();
    }

    @Override
    public BackOfficeUser getSingleBackOfficeUser(int id) {
        Optional<BackOfficeUser> opBoUser = boRepo.findById(id);
        if (opBoUser.isPresent()) {
            return opBoUser.get();
        } else {
            throw new BackOfficeException(BackOfficeExceptionMessages.BACK_OFFICE_USER_NOT_FOUND);
        }
    }

    @Override
    public void deleteBackOfficeUser(int id) {
        getSingleBackOfficeUser(id);
        boRepo.deleteById(id);
    }

}
