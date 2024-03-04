package io.biker.management.backOffice.mapper;

import org.springframework.stereotype.Component;

import io.biker.management.backOffice.dto.BackOfficeUserCreationDTO;
import io.biker.management.backOffice.dto.BackOfficeUserReadingDTO;
import io.biker.management.backOffice.entity.BackOfficeUser;

@Component
public class BackOfficeMapper {
    // To Dto
    public BackOfficeUserReadingDTO toDto(BackOfficeUser backOfficeUser) {
        BackOfficeUserReadingDTO dto = new BackOfficeUserReadingDTO(backOfficeUser.getId(), backOfficeUser.getName(),
                backOfficeUser.getEmail(), backOfficeUser.getPhoneNumber());

        return dto;
    }

    // To Entity
    public BackOfficeUser toBackOfficeUser(BackOfficeUserCreationDTO dto) {
        BackOfficeUser backOfficeUser = new BackOfficeUser();
        backOfficeUser.setName(dto.name());
        backOfficeUser.setEmail(dto.email());
        backOfficeUser.setPassword(dto.password());
        backOfficeUser.setPhoneNumber(dto.phoneNum());

        return backOfficeUser;
    }
}
