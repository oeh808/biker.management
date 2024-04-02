package io.biker.management.orderHistory.dtos;

import java.util.Date;

import io.biker.management.enums.OrderStatus;

public record OrderHistoryReadingDTO(int id, int orderId, Date orderCreationDate, String updatedAt,
        Date estimatedTimeOfArrival,
        OrderStatus status, int bikerId) {

}
