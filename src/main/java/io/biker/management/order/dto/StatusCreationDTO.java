package io.biker.management.order.dto;

import io.biker.management.constants.validation.ValidationMessages;
import io.biker.management.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record StatusCreationDTO(@Schema(requiredProperties = {
                ValidationMessages.NOT_NULL_SCHEMA }) @NotNull(message = "status"
                                + ValidationMessages.NOT_NULL) OrderStatus status){

}
