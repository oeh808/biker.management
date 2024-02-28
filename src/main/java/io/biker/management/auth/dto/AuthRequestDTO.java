package io.biker.management.auth.dto;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "username"
                        + ValidationMessages.NOT_BLANK) String username,
        @Schema(requiredProperties = { ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "password"
                + ValidationMessages.NOT_BLANK) String password){
}
