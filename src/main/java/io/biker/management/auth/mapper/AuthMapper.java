package io.biker.management.auth.mapper;

import org.springframework.stereotype.Component;

import io.biker.management.auth.Roles;
import io.biker.management.auth.dto.UserCreationDTO;
import io.biker.management.auth.entity.UserInfo;
import io.biker.management.biker.entity.Biker;

@Component
public class AuthMapper {
    // To Entity
    public UserInfo toUser_Biker(UserCreationDTO dto) {
        UserInfo user = new UserInfo();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setRoles(Roles.BIKER);

        return user;
    }

    public Biker toBiker(UserCreationDTO dto) {
        Biker biker = new Biker();
        biker.setName(dto.name());
        biker.setEmail(dto.username());
        biker.setPhoneNumber(dto.phoneNum());

        return biker;
    }
}
