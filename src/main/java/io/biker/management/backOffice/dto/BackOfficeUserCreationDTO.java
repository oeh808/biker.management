package io.biker.management.backOffice.dto;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record BackOfficeUserCreationDTO(
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "name"
                        + ValidationMessages.NOT_BLANK) String name,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "email"
                        + ValidationMessages.NOT_BLANK) String email,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "password"
                        + ValidationMessages.NOT_BLANK) String password,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "phone number"
                        + ValidationMessages.NOT_BLANK) String phoneNum){

}
