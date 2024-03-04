package io.biker.management.customer.dtos;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CustomerCreationDTO(
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
