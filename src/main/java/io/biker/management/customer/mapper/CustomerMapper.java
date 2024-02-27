package io.biker.management.customer.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.biker.management.customer.dtos.AddressCreationDTO;
import io.biker.management.user.Address;

@Component
public class CustomerMapper {
    // To Entity
    public List<Address> toAddresses(List<AddressCreationDTO> dtos) {
        List<Address> addresses = new ArrayList<Address>();

        for (AddressCreationDTO dto : dtos) {
            Address address = new Address(dto.street(), dto.city(), dto.state(), dto.postCode(), dto.country());
            addresses.add(address);
        }

        return addresses;
    }
}
