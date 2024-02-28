package io.biker.management.customer.mapper;

import org.springframework.stereotype.Component;

import io.biker.management.customer.dtos.AddressCreationDTO;
import io.biker.management.user.Address;

@Component
public class CustomerMapper {
    // To Entity

    public Address toAddress(AddressCreationDTO dto) {
        Address address = new Address(dto.street(), dto.city(), dto.state(), dto.postCode(), dto.country());

        return address;
    }
}
