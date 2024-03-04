package io.biker.management.customer.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.biker.management.customer.dtos.AddressCreationDTO;
import io.biker.management.customer.dtos.CustomerCreationDTO;
import io.biker.management.customer.dtos.CustomerReadingDTO;
import io.biker.management.customer.entity.Customer;
import io.biker.management.user.Address;

@Component
public class CustomerMapper {
    // To Dto
    public CustomerReadingDTO toDto(Customer customer) {
        CustomerReadingDTO dto = new CustomerReadingDTO(customer.getId(), customer.getName(), customer.getEmail(),
                customer.getPhoneNumber(), customer.getAddresses());

        return dto;
    }

    public List<CustomerReadingDTO> toDtos(List<Customer> customers) {
        List<CustomerReadingDTO> dtos = new ArrayList<>();
        for (Customer customer : customers) {
            dtos.add(toDto(customer));
        }

        return dtos;
    }

    // To Entity
    public Customer toCustomer(CustomerCreationDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setPhoneNumber(dto.phoneNum());
        customer.setPassword(dto.password());

        return customer;
    }

    public Address toAddress(AddressCreationDTO dto) {
        Address address = new Address(dto.street(), dto.city(), dto.state(), dto.postCode(), dto.country());

        return address;
    }
}
