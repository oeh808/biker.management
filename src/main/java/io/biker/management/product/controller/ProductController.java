package io.biker.management.product.controller;

import org.springframework.web.bind.annotation.RestController;

import io.biker.management.constants.Roles_Const;
import io.biker.management.constants.response.Responses;
import io.biker.management.errorHandling.responses.SuccessResponse;
import io.biker.management.product.dto.ProductCreationDTO;
import io.biker.management.product.dto.ProductReadingAdminDTO;
import io.biker.management.product.dto.ProductReadingDTO;
import io.biker.management.product.entity.Product;
import io.biker.management.product.mapper.ProductMapper;
import io.biker.management.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Log4j2
@RestController
@Tag(name = "Products", description = "Controller for handling mappings for products")
@SecurityRequirement(name = "Authorization")
public class ProductController {
        private ProductService productService;
        private ProductMapper productMapper;

        public ProductController(ProductService productService, ProductMapper productMapper) {
                this.productService = productService;
                this.productMapper = productMapper;
        }

        // Create
        @Operation(description = "POST endpoint for creating a product associated with a store identified by its storeId."
                        +
                        "\n\n Can only be done by stores managing their own products." +
                        "\n\n Returns the product created as an instance of ProductReadingDTO.", summary = "Create a product")
        @PostMapping("{storeId}/products")
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of ProductCreationDTO")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "') or " +
                        "(hasAuthority('" + Roles_Const.STORE + "') and #storeId == authentication.principal.id)")
        public ProductReadingDTO createProduct(
                        @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId,
                        @Valid @RequestBody ProductCreationDTO dto) {
                log.info("Recieved: POST request to /" + storeId + "/products");
                Product product = productService.createProduct(storeId, productMapper.toProduct(dto));
                return productMapper.toDto(product);
        }

        // Read
        @Operation(description = "GET endpoint for retrieving ALL products." +
                        "\n\n Can only be done by admins." +
                        "\n\n Returns all products as a List of ProductReadingAdminDTO.", summary = "Get ALL products")
        @GetMapping("/products")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "')")
        public List<ProductReadingAdminDTO> getAllProducts() {
                log.info("Recieved: GET request to /products");
                return productMapper.toDtosAdmim(productService.getAllProducts());
        }

        @Operation(description = "GET endpoint for retrieving all products associated with a store identified by its storeId."
                        +
                        "\n\n Can only be done by stores managing their own products." +
                        "\n\n Returns all of a store's products as a List of ProductReadingDTO.", summary = "Get all products from store")
        @GetMapping("{storeId}/products")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "') or " +
                        "(hasAuthority('" + Roles_Const.STORE + "') and #storeId == authentication.principal.id)")
        public List<ProductReadingDTO> getAllProductsByStore(
                        @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId) {
                log.info("Recieved: GET request to /" + storeId + "/products");
                return productMapper.toDtos(productService.getAllProductsByStore(storeId));
        }

        @Operation(description = "GET endpoint for retrieving a single product associated with a store identified by its storeId."
                        +
                        "\n\n Can only be done by stores managing their own products." +
                        "\n\n Returns the product as an instance of ProductReadingDTO.", summary = "Get a single product")
        @GetMapping("{storeId}/products/{id}")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "') or " +
                        "(hasAuthority('" + Roles_Const.STORE + "') and #storeId == authentication.principal.id)")
        public ProductReadingDTO getSingleProduct(
                        @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId,
                        @Parameter(in = ParameterIn.PATH, name = "id", description = "Product ID") @PathVariable int id) {
                log.info("Recieved: GET request to /" + storeId + "/products/" + id);
                return productMapper.toDto(productService.getSingleProduct(storeId, id));
        }

        // Update
        @Operation(description = "PUT endpoint for updating a product associated with a store identified by its storeId."
                        +
                        "\n\n Can only be done by stores managing their own products." +
                        "\n\n Returns the product as an instance of ProductReadingDTO.", summary = "Update a product")
        @PutMapping("{storeId}/products/{id}")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "') or " +
                        "(hasAuthority('" + Roles_Const.STORE + "') and #storeId == authentication.principal.id)")
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of ProductCreationDTO")
        public ProductReadingDTO updateProduct(
                        @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId,
                        @Parameter(in = ParameterIn.PATH, name = "id", description = "Product ID") @PathVariable int id,
                        @Valid @RequestBody ProductCreationDTO dto) {
                log.info("Recieved: PUT request to /" + storeId + "/products/" + id);
                Product product = productMapper.toProduct(dto);
                product.setProductId(id);

                return productMapper.toDto(productService.updateProduct(storeId, product));
        }

        // Delete
        @Operation(description = "DELETE endpoint for deleting a product associated with a store identified by its storeId."
                        +
                        "\n\n Can only be done by stores managing their own products." +
                        "\n\n Returns a response as an instance of ResponseEntity<SuccessResponse>.", summary = "Delete a product")
        @DeleteMapping("{storeId}/products/{id}")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "') or " +
                        "(hasAuthority('" + Roles_Const.STORE + "') and #storeId == authentication.principal.id)")
        public ResponseEntity<SuccessResponse> deleteProduct(
                        @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId,
                        @Parameter(in = ParameterIn.PATH, name = "id", description = "Product ID") @PathVariable int id) {
                log.info("Recieved: DELETE request to /" + storeId + "/products/" + id);
                productService.deleteProduct(storeId, id);
                return new ResponseEntity<SuccessResponse>(new SuccessResponse(Responses.PRODUCT_DELETED),
                                HttpStatus.ACCEPTED);
        }
}
