package io.biker.management.customer.dtos;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AddressCreationDTO(
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK }) @NotBlank(message = ValidationMessages.NOT_BLANK) String street,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK }) @NotBlank(message = ValidationMessages.NOT_BLANK) String city,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK }) @NotBlank(message = ValidationMessages.NOT_BLANK) String state,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK }) @NotBlank(message = ValidationMessages.NOT_BLANK) String postCode,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK }) @NotBlank(message = ValidationMessages.NOT_BLANK) String country){
}