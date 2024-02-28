package io.biker.management.product.controller;

import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.Roles;
import io.biker.management.auth.response.Responses;
import io.biker.management.error_handling.responses.SuccessResponse;
import io.biker.management.product.dto.ProductCreationDTO;
import io.biker.management.product.dto.ProductReadingDTO;
import io.biker.management.product.entity.Product;
import io.biker.management.product.mapper.ProductMapper;
import io.biker.management.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@SecurityRequirement(name = "Authorization")
@Tag(name = "Products")
public class ProductController {
    private ProductService productService;
    private ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    // Create
    @Operation(description = "POST endpoint for creating a product associated with a store identified by its storeId." +
            "\n\n Can only be done by admins or stores managing their own products.", summary = "Create a product")
    @PostMapping("{storeId}/products")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of ProductCreationDTO")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "') or " +
            "(hasAuthority('" + Roles.STORE + "') and #storeId == authentication.principal.id)")
    public ProductReadingDTO createProduct(
            @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId,
            @RequestBody ProductCreationDTO dto) {
        Product product = productService.createProduct(storeId, productMapper.toProduct(dto));
        return productMapper.toDto(product);
    }

    // Read
    @Operation(description = "GET endpoint for retrieving ALL products." +
            "\n\n Can only be done by admins.", summary = "Get ALL products")
    @GetMapping("/products")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
    public List<ProductReadingDTO> getAllProducts() {
        return productMapper.toDtos(productService.getAllProducts());
    }

    @Operation(description = "GET endpoint for retrieving all products associated with a store identified by its storeId."
            +
            "\n\n Can only be done by admins or stores managing their own products.", summary = "Get all products from store")
    @GetMapping("{storeId}/products")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "') or " +
            "(hasAuthority('" + Roles.STORE + "') and #storeId == authentication.principal.id)")
    public List<ProductReadingDTO> getAllProductsByStore(
            @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId) {
        return productMapper.toDtos(productService.getAllProductsByStore(storeId));
    }

    @Operation(description = "GET endpoint for retrieving a single product associated with a store identified by its storeId."
            +
            "\n\n Can only be done by admins or stores managing their own products.", summary = "Get a single product")
    @GetMapping("{storeId}/products/{id}")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "') or " +
            "(hasAuthority('" + Roles.STORE + "') and #storeId == authentication.principal.id)")
    public ProductReadingDTO getSingleProduct(
            @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId,
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Product ID") @PathVariable int id) {
        return productMapper.toDto(productService.getSingleProduct(storeId, id));
    }

    // Update
    @Operation(description = "PUT endpoint for updating a product associated with a store identified by its storeId." +
            "\n\n Can only be done by admins or stores managing their own products.", summary = "Update a product")
    @PutMapping("{storeId}/products/{id}")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "') or " +
            "(hasAuthority('" + Roles.STORE + "') and #storeId == authentication.principal.id)")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of ProductCreationDTO")
    public ProductReadingDTO updateProduct(
            @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId,
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Product ID") @PathVariable int id,
            @RequestBody ProductCreationDTO dto) {
        Product product = productMapper.toProduct(dto);
        product.setProductId(id);

        return productMapper.toDto(productService.updateProduct(storeId, product));
    }

    // Delete
    @Operation(description = "DELETE endpoint for deleting a product associated with a store identified by its storeId."
            +
            "\n\n Can only be done by admins or stores managing their own products.", summary = "Delete a product")
    @DeleteMapping("{storeId}/products/{id}")
    @PreAuthorize("hasAuthority('" + Roles.ADMIN + "') or " +
            "(hasAuthority('" + Roles.STORE + "') and #storeId == authentication.principal.id)")
    public SuccessResponse deleteProduct(
            @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId,
            @Parameter(in = ParameterIn.PATH, name = "id", description = "Product ID") @PathVariable int id) {
        productService.deleteProduct(storeId, id);
        SuccessResponse successResponse = new SuccessResponse(Responses.PRODUCT_DELETED);

        return successResponse;
    }
}
