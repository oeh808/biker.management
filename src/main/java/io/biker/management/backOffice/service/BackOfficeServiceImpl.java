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
    private BackOfficeUserRepo backOfficeRepo;

    public BackOfficeServiceImpl(BackOfficeUserRepo backOfficeRepo) {
        this.backOfficeRepo = backOfficeRepo;
    }

    @Override
    public BackOfficeUser createBackOfficeUser(BackOfficeUser backOfficeUser) {
        return backOfficeRepo.save(backOfficeUser);
    }

    @Override
    public List<BackOfficeUser> getAllBackOfficeUsers() {
        return backOfficeRepo.findAll();
    }

    @Override
    public BackOfficeUser getSingleBackOfficeUser(int id) {
        Optional<BackOfficeUser> opBackOfficeUser = backOfficeRepo.findById(id);
        if (opBackOfficeUser.isPresent()) {
            return opBackOfficeUser.get();
        } else {
            throw new BackOfficeException(BackOfficeExceptionMessages.BACK_OFFICE_USER_NOT_FOUND);
        }
    }

    @Override
    public void deleteBackOfficeUser(int id) {
        getSingleBackOfficeUser(id);
        backOfficeRepo.deleteById(id);
    }

}
