package io.biker.management.auth.dto;

import io.biker.management.constants.validation.ValidationMessages;
import io.biker.management.user.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StoreCreationDTO(
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK }) @NotBlank(message = ValidationMessages.NOT_BLANK) String name,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK }) @NotBlank(message = ValidationMessages.NOT_BLANK) String password,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_NULL }) @NotNull(message = ValidationMessages.NOT_NULL) Address address,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK }) @NotBlank(message = ValidationMessages.NOT_BLANK) String phoneNum){

}
