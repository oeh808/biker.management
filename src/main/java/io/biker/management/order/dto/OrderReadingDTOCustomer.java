package io.biker.management.order.dto;

import java.util.Date;

import io.biker.management.user.Address;

public record OrderReadingDTOCustomer(String status, Date estimatedTimeOfArrival, String product,
        float price, float VAT, float totalCost,
        String biker, Address deliveryAddress) {

}
