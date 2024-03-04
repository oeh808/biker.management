package io.biker.management.store.dto;

import java.util.List;

import io.biker.management.user.Address;

public record StoreReadingDTO(String name, Address address, String phoneNum,
        List<ProductCustomerReadingDTO> products) {

}
