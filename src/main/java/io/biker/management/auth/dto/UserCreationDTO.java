package io.biker.management.auth.dto;

import io.biker.management.constants.validation.ValidationMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UserCreationDTO(
                @Schema(requiredProperties = {
                                "Cannot be null",
                                "Cannot be empty" }) @NotBlank(message = ValidationMessages.NOT_BLANK) String name,
                @Schema(requiredProperties = {
                                "Cannot be null",
                                "Cannot be empty" }) @NotBlank(message = ValidationMessages.NOT_BLANK) String username,
                @Schema(requiredProperties = {
                                "Cannot be null",
                                "Cannot be empty" }) @NotBlank(message = ValidationMessages.NOT_BLANK) String password,
                @Schema(requiredProperties = {
                                "Cannot be null",
                                "Cannot be empty" }) @NotBlank(message = ValidationMessages.NOT_BLANK) String phoneNum){
}
