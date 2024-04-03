package io.biker.management.order.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import io.biker.management.biker.entity.Biker;
import io.biker.management.customer.entity.Customer;
import io.biker.management.enums.OrderStatus;
import io.biker.management.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Orders")
public class Order {
    @Transient
    public static final String SEQUENCE_NAME = "ordersSequence";

    @Id
    private int orderId;
    private Customer customer;
    private Store store;
    private Biker biker;
    private OrderStatus status;
    private Date estimatedTimeOfArrival;
    private OrderDetails orderDetails;
}
