package io.biker.management.customer.dtos;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerCreationDTO(
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "name"
                        + ValidationMessages.NOT_BLANK) String name,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "email"
                        + ValidationMessages.NOT_BLANK) @Email(message = ValidationMessages.INVALID_EMAIL, regexp = ValidationMessages.EMAIL_REGEX) String email,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "password"
                        + ValidationMessages.NOT_BLANK) String password,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "phone number"
                        + ValidationMessages.NOT_BLANK) @Pattern(message = ValidationMessages.INVALID_PHONE_NUMBER, regexp = ValidationMessages.PHONE_NUMBER_REGEX) String phoneNum){
}
