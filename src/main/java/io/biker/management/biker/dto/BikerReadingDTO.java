package io.biker.management.biker.dto;

import io.biker.management.user.Address;

public record BikerReadingDTO(int id, String name, String email, String phoneNum, Address currentLocation) {

}
