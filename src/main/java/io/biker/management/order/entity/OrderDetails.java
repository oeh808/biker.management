package io.biker.management.order.entity;

import io.biker.management.product.entity.Product;
import io.biker.management.user.Address;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderDetails {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;
    private float VAT;
    private float totalCost;
    @Embedded
    private Address address;
    @Embedded
    private FeedBack feedBack;
}
