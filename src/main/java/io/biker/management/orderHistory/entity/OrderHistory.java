package io.biker.management.orderHistory.entity;

import java.sql.Date;
import java.time.ZonedDateTime;

import io.biker.management.biker.entity.Biker;
import io.biker.management.enums.OrderStatus;
import io.biker.management.order.entity.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date orderCreationDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Date estimatedTimeOfArrival;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bikerId")
    private Biker biker;

    private ZonedDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;
}
