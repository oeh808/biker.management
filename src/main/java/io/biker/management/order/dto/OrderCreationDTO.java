package io.biker.management.order.dto;

import io.biker.management.constants.validation.ValidationMessages;
import io.biker.management.customer.dtos.AddressCreationDTO;
import io.biker.management.customer.entity.Customer;
import io.biker.management.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record OrderCreationDTO(
        @Schema(requiredProperties = {
                ValidationMessages.NOT_NULL }) @NotNull(message = "name"
                        + ValidationMessages.NOT_NULL) Customer customer,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_NULL_SCHEMA }) @NotNull(message = "product"
                        + ValidationMessages.NOT_NULL) Product product,
        @Schema(requiredProperties = {
                ValidationMessages.NOT_NULL }) @NotNull(message = "address"
                        + ValidationMessages.NOT_NULL) AddressCreationDTO address){

}
