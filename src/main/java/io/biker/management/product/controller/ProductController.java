package io.biker.management.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.response.Responses;
import io.biker.management.error_handling.responses.SuccessResponse;
import io.biker.management.product.dto.ProductCreationDTO;
import io.biker.management.product.dto.ProductReadingDTO;
import io.biker.management.product.entity.Product;
import io.biker.management.product.mapper.ProductMapper;
import io.biker.management.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Tag(name = "Products", description = "Can only be accessed by stores to manage their products.")
@RequestMapping("/{/storeId}/products")
public class ProductController {
    private ProductService productService;
    private ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    // Create
    @PostMapping()
    public ProductReadingDTO createProduct(@RequestBody ProductCreationDTO dto) {
        Product product = productService.createProduct(productMapper.toProduct(dto));
        return productMapper.toDto(product);
    }

    // Read
    @GetMapping()
    public List<ProductReadingDTO> getAllProducts() {
        return productMapper.toDtos(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ProductReadingDTO getSingleProduct(@PathVariable int id) {
        return productMapper.toDto(productService.getSingleProduct(id));
    }

    // Update
    @PutMapping("/{id}")
    public ProductReadingDTO updateProduct(@PathVariable int storeId, @PathVariable int id,
            @RequestBody ProductCreationDTO dto) {
        Product product = productMapper.toProduct(dto);
        product.setProductId(id);

        return productMapper.toDto(productService.updateProduct(storeId, product));
    }

    // Delete
    @DeleteMapping("/{id}")
    public SuccessResponse deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        SuccessResponse successResponse = new SuccessResponse(Responses.PRODUCT_DELETED);

        return successResponse;
    }
}
