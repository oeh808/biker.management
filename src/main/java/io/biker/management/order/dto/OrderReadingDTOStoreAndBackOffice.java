package io.biker.management.order.dto;

import java.sql.Date;

import io.biker.management.order.entity.FeedBack;
import io.biker.management.user.Address;

public record OrderReadingDTOStoreAndBackOffice(int orderId, int customerId, int storeId, int bikerId, int productId,
        String status, Date eta, Address deliveryAddress, float totalCost, FeedBack feedBack) {
}
