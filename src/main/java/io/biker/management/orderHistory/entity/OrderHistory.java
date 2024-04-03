package io.biker.management.orderHistory.entity;

import java.util.Date;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import io.biker.management.biker.entity.Biker;
import io.biker.management.enums.OrderStatus;
import io.biker.management.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Orders History")
public class OrderHistory {
    @Transient
    public static final String SEQUENCE_NAME = "ordersHistorySequence";
    
    @Id
    private int id;
    private Date orderCreationDate;
    private OrderStatus status;
    private Date estimatedTimeOfArrival;
    private Biker biker;
    private Instant updatedAt;
    private Order order;
}
