package io.biker.management.order.entity;

import io.biker.management.product.entity.Product;
import io.biker.management.user.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    private Product product;
    private float VAT;
    private float totalCost;
    private Address address;
    private FeedBack feedBack;
}
