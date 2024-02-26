package io.biker.management.auth.dto;

import io.biker.management.constants.validation.ValidationMessages;
import jakarta.validation.constraints.NotBlank;

public record UserCreationDTO(
        @NotBlank(message = ValidationMessages.NOT_BLANK) String name,
        @NotBlank(message = ValidationMessages.NOT_BLANK) String username,
        @NotBlank(message = ValidationMessages.NOT_BLANK) String password,
        @NotBlank(message = ValidationMessages.NOT_BLANK) String phoneNum) {
}
