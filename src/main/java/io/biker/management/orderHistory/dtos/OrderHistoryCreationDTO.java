package io.biker.management.orderHistory.dtos;

import java.util.Date;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record OrderHistoryCreationDTO(
                @Schema(requiredProperties = {
                                ValidationMessages.NOT_NULL_SCHEMA,
                                ValidationMessages.PAST_OR_PRESENT_SCHEMA }) @NotNull(message = "orderCreationDate"
                                                + ValidationMessages.NOT_NULL) @PastOrPresent(message = "orderCreationDate"
                                                                + ValidationMessages.PAST_OR_PRESENT) Date orderCreationDate){
}
