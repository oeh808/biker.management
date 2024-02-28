package io.biker.management.product.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.biker.management.product.dto.ProductCreationDTO;
import io.biker.management.product.dto.ProductReadingDTO;
import io.biker.management.product.entity.Product;

@Component
public class ProductMapper {
    // To Dto
    public ProductReadingDTO toDto(Product product) {
        ProductReadingDTO dto = new ProductReadingDTO(product.getProductId(), product.getName(), product.getPrice(),
                product.getQuantity());

        return dto;
    }

    public List<ProductReadingDTO> toDtos(List<Product> products) {
        List<ProductReadingDTO> dtos = new ArrayList<>();
        for (Product product : products) {
            dtos.add(toDto(product));
        }

        return dtos;
    }

    // To Entity
    public Product toProduct(ProductCreationDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());

        return product;
    }

}
