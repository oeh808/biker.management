package io.biker.management.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.biker.management.auth.entity.UserRoles;
import io.biker.management.auth.exception.AuthExceptionMessages;
import io.biker.management.auth.exception.CustomAuthException;
import io.biker.management.auth.repo.UserRolesRepo;
import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.backOffice.service.BackOfficeService;
import io.biker.management.biker.entity.Biker;
import io.biker.management.biker.service.BikerService;
import io.biker.management.constants.Roles_Const;
import io.biker.management.customer.entity.Customer;
import io.biker.management.customer.service.CustomerService;
import io.biker.management.store.entity.Store;
import io.biker.management.store.service.StoreService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserRolesServiceImpl implements UserRolesService {
    private UserRolesRepo repository;
    private CustomerService customerService;
    private BikerService bikerService;
    private BackOfficeService backOfficeService;
    private StoreService storeService;

    public UserRolesServiceImpl(UserRolesRepo repository, CustomerService customerService, BikerService bikerService,
            BackOfficeService backOfficeService, StoreService storeService) {
        this.repository = repository;
        this.customerService = customerService;
        this.bikerService = bikerService;
        this.backOfficeService = backOfficeService;
        this.storeService = storeService;
    }

    @Override
    public UserRoles addUser(UserRoles userRoles) {
        log.info("Running addUser(" + userRoles + ") in UserRolesServiceImpl...");
        if (isDuplicatePhoneNumber(userRoles.getUser().getPhoneNumber())
                || isDuplicateUsername(userRoles.getUser().getEmail())) {
            throw new CustomAuthException(AuthExceptionMessages.DUPLICATE_DATA);
        }

        log.info("Saving user registered with roles...");
        return repository.save(userRoles);
    }

    @Override
    public UserRoles registerCustomer(int id) {
        log.info("Running registerCustomer(" + id + ") in UserRolesServiceImpl...");
        Customer customer = customerService.getSingleCustomer(id);

        log.info("Assigning roles..");
        List<String> roles = new ArrayList<>();
        roles.add(Roles_Const.CUSTOMER);

        UserRoles user = new UserRoles(id, customer, roles);
        return addUser(user);
    }

    @Override
    public UserRoles registerBiker(int id) {
        log.info("Running registerBiker(" + id + ") in UserRolesServiceImpl...");
        Biker biker = bikerService.getSingleBiker(id);

        log.info("Assigning roles..");
        List<String> roles = new ArrayList<>();
        roles.add(Roles_Const.BIKER);

        UserRoles user = new UserRoles(id, biker, roles);
        return addUser(user);
    }

    @Override
    public UserRoles registerStore(int id) {
        log.info("Running registerStore(" + id + ") in UserRolesServiceImpl...");
        Store store = storeService.getSingleStore(id);

        log.info("Assigning roles..");
        List<String> roles = new ArrayList<>();
        roles.add(Roles_Const.STORE);

        UserRoles user = new UserRoles(id, store, roles);
        return addUser(user);
    }

    @Override
    public UserRoles registerBackOfficeUser(int id) {
        log.info("Running registerBackOfficeUser(" + id + ") in UserRolesServiceImpl...");
        BackOfficeUser backOfficeUser = backOfficeService.getSingleBackOfficeUser(id);

        log.info("Assigning roles..");
        List<String> roles = new ArrayList<>();
        roles.add(Roles_Const.BACK_OFFICE);

        UserRoles user = new UserRoles(id, backOfficeUser, roles);
        return addUser(user);
    }

    @Override
    public void deleteUser(int id) {
        log.info("Running deleteUser(" + id + ") in UserRolesServiceImpl...");
        if (!userExists(id)) {
            throw new CustomAuthException(AuthExceptionMessages.USER_DOES_NOT_EXIST);
        }

        log.info("Deleting user registered with roles...");
        repository.deleteById(id);
    }

    // Helper functions
    private boolean isDuplicateUsername(String username) {
        log.info("Checking that email: " + username + " does not exist in the user roles table...");
        Optional<UserRoles> opUser = repository.findByUser_Email(username);
        if (opUser.isPresent()) {
            log.error("Duplicate email detected!");
        }

        return opUser.isPresent();
    }

    private boolean isDuplicatePhoneNumber(String phoneNum) {
        log.info("Checking that phone number: " + phoneNum + " does not exist in the user roles table...");
        Optional<UserRoles> opUser = repository.findByUser_PhoneNumber(phoneNum);
        if (opUser.isPresent()) {
            log.error("Duplicate phone number detected!");
        }

        return opUser.isPresent();
    }

    private boolean userExists(int id) {
        log.info("Checking that user with id: " + id + " exists...");
        Optional<UserRoles> opUser = repository.findById(id);

        if (opUser.isEmpty()) {
            log.error("Invalid user id: " + id + "!");
        }
        return opUser.isPresent();
    }
}
