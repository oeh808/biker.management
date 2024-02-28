package io.biker.management.customer.dtos;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AddressCreationDTO(
                @Schema(requiredProperties = {
                                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "street"
                                                + ValidationMessages.NOT_BLANK) String street,
                @Schema(requiredProperties = {
                                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "city"
                                                + ValidationMessages.NOT_BLANK) String city,
                @Schema(requiredProperties = {
                                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "state"
                                                + ValidationMessages.NOT_BLANK) String state,
                @Schema(requiredProperties = {
                                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "postcode"
                                                + ValidationMessages.NOT_BLANK) String postCode,
                @Schema(requiredProperties = {
                                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "country"
                                                + ValidationMessages.NOT_BLANK) String country){
}
