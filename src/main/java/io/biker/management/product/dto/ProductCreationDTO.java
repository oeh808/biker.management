package io.biker.management.product.dto;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductCreationDTO(
                @Schema(requiredProperties = {
                                ValidationMessages.NOT_BLANK_SCHEMA }) @NotBlank(message = "name"
                                                + ValidationMessages.NOT_BLANK) String name,
                @Schema(minimum = "1") @Positive(message = "price"
                                + ValidationMessages.POSITIVE) float price,
                @Schema(minimum = "0") @PositiveOrZero(message = "quantity"
                                + ValidationMessages.POSITIVE_OR_ZERO) int quantity){
}
