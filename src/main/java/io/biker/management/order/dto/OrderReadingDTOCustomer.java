package io.biker.management.order.dto;

import java.sql.Date;

import io.biker.management.user.Address;

public record OrderReadingDTOCustomer(String status, Date eta, String product,
                float price, float VAT, float totalCost,
                String biker, Address deliveryAddress) {

}
