package io.biker.management.order.entity;

import io.biker.management.user.Address;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderDetails {
    private String productName;
    private float price;
    private float VAT;
    private float totalCost;
    @Embedded
    private Address address;
    @Embedded
    private FeedBack feedBack;
}
