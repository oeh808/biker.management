package io.biker.management.auth.mapper;

import org.springframework.stereotype.Component;

import io.biker.management.auth.Roles;
import io.biker.management.auth.dto.StoreCreationDTO;
import io.biker.management.auth.dto.UserCreationDTO;
import io.biker.management.auth.entity.UserInfo;
import io.biker.management.backOffice.entity.BackOfficeUser;
import io.biker.management.biker.entity.Biker;
import io.biker.management.customer.entity.Customer;
import io.biker.management.store.entity.Store;

@Component
public class AuthMapper {
    // To UserInfo
    public UserInfo toUserCustomer(UserCreationDTO dto) {
        UserInfo user = new UserInfo();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setPhoneNumber(dto.phoneNum());
        user.setRoles(Roles.CUSTOMER);

        return user;
    }

    public UserInfo toUserBiker(UserCreationDTO dto) {
        UserInfo user = new UserInfo();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setPhoneNumber(dto.phoneNum());
        user.setRoles(Roles.BIKER);

        return user;
    }

    public UserInfo toUserBackOfficeUser(UserCreationDTO dto) {
        UserInfo user = new UserInfo();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setPhoneNumber(dto.phoneNum());
        user.setRoles(Roles.BACK_OFFICE);

        return user;
    }

    public UserInfo toUserStore(StoreCreationDTO dto) {
        UserInfo user = new UserInfo();
        user.setUsername(dto.name());
        user.setPassword(dto.password());
        user.setPhoneNumber(dto.phoneNum());
        user.setRoles(Roles.STORE);

        return user;
    }

    // To Entity
    public Customer toCustomer(UserCreationDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.username());
        customer.setPhoneNumber(dto.phoneNum());

        return customer;
    }

    public Biker toBiker(UserCreationDTO dto) {
        Biker biker = new Biker();
        biker.setName(dto.name());
        biker.setEmail(dto.username());
        biker.setPhoneNumber(dto.phoneNum());

        return biker;
    }

    public BackOfficeUser toBackOfficeUser(UserCreationDTO dto) {
        BackOfficeUser backOfficeUser = new BackOfficeUser();
        backOfficeUser.setName(dto.name());
        backOfficeUser.setEmail(dto.username());
        backOfficeUser.setPhoneNumber(dto.password());

        return backOfficeUser;
    }

    public Store toStore(StoreCreationDTO dto) {
        Store store = new Store();
        store.setName(dto.name());
        store.setAddress(dto.address());
        store.setPhoneNumber(dto.phoneNum());

        return store;
    }
}
