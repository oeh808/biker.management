package io.biker.management.order.dto;

import java.sql.Date;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record EstimatedTimeOfArrivalCreationDTO(
        @Schema(requiredProperties = {
                ValidationMessages.FUTURE_SCHEMA,
                ValidationMessages.NOT_NULL_SCHEMA }) @NotNull(message = "Estimated time of arrival"
                        + ValidationMessages.NOT_NULL) @Future(message = "Estimated time of arrival"
                                + ValidationMessages.FUTURE) Date estimatedTimeOfArrival){
}
