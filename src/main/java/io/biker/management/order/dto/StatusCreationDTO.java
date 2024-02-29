package io.biker.management.order.dto;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record StatusCreationDTO(@Schema(requiredProperties = {
        ValidationMessages.NOT_BLANK }) @NotBlank(message = "status"
                + ValidationMessages.NOT_BLANK) String status){

}
