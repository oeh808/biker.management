package io.biker.management.store.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.biker.management.product.entity.Product;
import io.biker.management.store.dto.ProductCustomerReadingDTO;
import io.biker.management.store.dto.StoreReadingDTO;
import io.biker.management.store.entity.Store;

@Component
public class StoreMapper {
    // To DTO
    public StoreReadingDTO toReadingDto(Store store) {
        List<ProductCustomerReadingDTO> productsDto = new ArrayList<>();
        List<Product> products = store.getProducts();

        for (Product product : products) {
            // Only products that are in stock are retrieved
            if (product.getQuantity() > 0) {
                productsDto.add(new ProductCustomerReadingDTO(product.getName(), product.getPrice()));
            }
        }

        StoreReadingDTO dto = new StoreReadingDTO(store.getName(), store.getAddress(), store.getPhoneNumber(),
                productsDto);

        return dto;
    }

    public List<StoreReadingDTO> toReadingDtos(List<Store> stores) {
        List<StoreReadingDTO> dtos = new ArrayList<>();

        for (Store store : stores) {
            dtos.add(toReadingDto(store));
        }

        return dtos;
    }
}
