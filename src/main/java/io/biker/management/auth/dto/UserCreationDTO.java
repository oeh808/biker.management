package io.biker.management.auth.dto;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserCreationDTO(
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "name"
                        + ValidationMessages.NOT_BLANK) String name,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "username"
                        + ValidationMessages.NOT_BLANK) String username,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "password"
                        + ValidationMessages.NOT_BLANK) String password,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "phoneNum"
                        + ValidationMessages.NOT_BLANK) String phoneNum){
}
