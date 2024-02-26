package io.biker.management.auth.mapper;

import org.springframework.stereotype.Component;

import io.biker.management.auth.Roles;
import io.biker.management.auth.dto.UserCreationDTO;
import io.biker.management.auth.entity.UserInfo;
import io.biker.management.back_office.entity.BackOfficeUser;
import io.biker.management.biker.entity.Biker;

@Component
public class AuthMapper {
    // To UserInfo
    public UserInfo toUser_Biker(UserCreationDTO dto) {
        UserInfo user = new UserInfo();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setRoles(Roles.BIKER);

        return user;
    }

    public UserInfo toUser_BoUser(UserCreationDTO dto) {
        UserInfo user = new UserInfo();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setRoles(Roles.BACK_OFFICE);

        return user;
    }

    // To Entity
    public Biker toBiker(UserCreationDTO dto) {
        Biker biker = new Biker();
        biker.setName(dto.name());
        biker.setEmail(dto.username());
        biker.setPhoneNumber(dto.phoneNum());

        return biker;
    }

    public BackOfficeUser toBoUser(UserCreationDTO dto) {
        BackOfficeUser boUser = new BackOfficeUser();
        boUser.setName(dto.name());
        boUser.setEmail(dto.username());
        boUser.setPhoneNumber(dto.password());

        return boUser;
    }
}
