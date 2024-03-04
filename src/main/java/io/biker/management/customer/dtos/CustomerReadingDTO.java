package io.biker.management.customer.dtos;

import java.util.Set;

import io.biker.management.user.Address;

public record CustomerReadingDTO(int id, String name, String email, String phoneNum, Set<Address> addresses) {

}
