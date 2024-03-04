package io.biker.management.auth.mapper;

import org.springframework.stereotype.Component;

import io.biker.management.auth.Roles;
import io.biker.management.auth.dto.UserCreationDTO;
import io.biker.management.auth.entity.UserInfo;
import io.biker.management.backOffice.entity.BackOfficeUser;

@Component
public class AuthMapper {
    // To UserInfo
    public UserInfo toUserBackOfficeUser(UserCreationDTO dto) {
        UserInfo user = new UserInfo();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setPhoneNumber(dto.phoneNum());
        user.setRoles(Roles.BACK_OFFICE);

        return user;
    }

    // To Entity
    public BackOfficeUser toBackOfficeUser(UserCreationDTO dto) {
        BackOfficeUser backOfficeUser = new BackOfficeUser();
        backOfficeUser.setName(dto.name());
        backOfficeUser.setEmail(dto.username());
        backOfficeUser.setPhoneNumber(dto.password());

        return backOfficeUser;
    }
}
