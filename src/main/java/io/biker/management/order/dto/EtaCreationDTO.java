package io.biker.management.order.dto;

import java.sql.Date;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;

public record EtaCreationDTO(
        @Schema(requiredProperties = {
                ValidationMessages.FUTURE_SCHEMA }) @Future(message = "Eta" + ValidationMessages.FUTURE) Date eta){
}
