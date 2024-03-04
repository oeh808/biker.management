package io.biker.management.customer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.Roles;
import io.biker.management.constants.response.Responses;
import io.biker.management.customer.dtos.AddressCreationDTO;
import io.biker.management.customer.dtos.CustomerCreationDTO;
import io.biker.management.customer.dtos.CustomerReadingDTO;
import io.biker.management.customer.entity.Customer;
import io.biker.management.customer.mapper.CustomerMapper;
import io.biker.management.customer.service.CustomerService;
import io.biker.management.errorHandling.responses.SuccessResponse;
import io.biker.management.user.Address;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Set;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@Tag(name = "Customers")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/customers")
public class CustomerController {
        private CustomerService customerService;
        private CustomerMapper customerMapper;

        public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
                this.customerService = customerService;
                this.customerMapper = customerMapper;
        }

        @Operation(description = "POST endpoint for creating a customers.", summary = "Create a customer")
        @PostMapping()
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of CustomerCreationDTO")
        public CustomerReadingDTO createCustomer(@Valid @RequestBody CustomerCreationDTO dto) {
                Customer customer = customerService.createCustomer(customerMapper.toCustomer(dto));

                return customerMapper.toDto(customer);
        }

        @Operation(description = "GET endpoint for retrieving all customers." +
                        "\n\n Can only be done by admins", summary = "Get all customers")
        @GetMapping()
        @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
        public List<CustomerReadingDTO> getAllCustomers() {
                return customerMapper.toDtos(customerService.getAllCustomers());
        }

        @Operation(description = "GET endpoint for retrieving a single customer given their id." +
                        "\n\n Can only be done by admins or the customer being retrieved.", summary = "Get single customer")
        @GetMapping("/{id}")
        @PreAuthorize("hasAuthority('" + Roles.ADMIN + "') or " +
                        "(hasAuthority('" + Roles.CUSTOMER + "') and #id == authentication.principal.id)")
        public CustomerReadingDTO getSingleCustomer(
                        @Parameter(in = ParameterIn.PATH, name = "id", description = "Customer ID") @PathVariable int id) {
                return customerMapper.toDto(customerService.getSingleCustomer(id));
        }

        @Operation(description = "PUT endpoint for a delivery address to a customer given their id." +
                        "\n\n Can only be done by admins or the customer whose address is being set.", summary = "Add customer delivery addresses")
        @PutMapping("/{id}/addresses")
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of AddressCreationDTO")
        @PreAuthorize("hasAuthority('" + Roles.ADMIN + "') or " +
                        "(hasAuthority('" + Roles.CUSTOMER + "') and #id == authentication.principal.id)")
        public Set<Address> addDeliveryAddress(
                        @Parameter(in = ParameterIn.PATH, name = "id", description = "Customer ID") @PathVariable int id,
                        @Valid @RequestBody AddressCreationDTO dto) {
                return customerService.addDeliveryAddress(id, customerMapper.toAddress(dto));
        }

        @Operation(description = "DELETE endpoint for deleting a customer from the customer table." +
                        "\n\n Can only be done by admins.", summary = "Delete Customer")
        @Transactional
        @DeleteMapping("/{id}")
        @SecurityRequirement(name = "Authorization")
        @PreAuthorize("hasAuthority('" + Roles.ADMIN + "')")
        public SuccessResponse deleteCustomer(
                        @Parameter(in = ParameterIn.PATH, name = "id", description = "Customer ID") @PathVariable int id) {
                customerService.deleteCustomer(id);
                return new SuccessResponse(Responses.USER_DELETED);
        }

}
