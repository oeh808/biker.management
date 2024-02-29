package io.biker.management.order.dto;

import java.sql.Date;

import io.biker.management.user.Address;

public record OrderReadingDTOBiker(Date eta, String product,
                float totalCost, String customer, String customerEmail,
                String customerPhoneNum, Address deliveryAddress) {

}
