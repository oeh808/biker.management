package io.biker.management.product.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.biker.management.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private int productId;
    private String name;
    private float price;
    private int quantity;
    private Store store;
}
