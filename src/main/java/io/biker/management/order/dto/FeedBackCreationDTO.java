package io.biker.management.order.dto;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record FeedBackCreationDTO(
        @Schema(minimum = "1", maximum = "5") @Min(value = 1, message = "rating"
                + ValidationMessages.POSITIVE) @Max(value = 5, message = "rating"
                        + ValidationMessages.LOE_TO_FIVE) int rating,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_BLANK }) @NotBlank(message = "review"
                        + ValidationMessages.NOT_BLANK) String review){
}
