package io.biker.management.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.error_handling.responses.SuccessResponse;
import io.biker.management.order.dto.ContactInfoDTO;
import io.biker.management.order.dto.FeedBackCreationDTO;
import io.biker.management.order.dto.OrderCreationDTO;
import io.biker.management.order.dto.OrderReadingDTOBiker;
import io.biker.management.order.dto.OrderReadingDTOCustomer;
import io.biker.management.order.dto.StatusCreationDTO;
import io.biker.management.order.entity.Order;
import io.biker.management.order.mapper.OrderMapper;
import io.biker.management.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Tag(name = "Orders")
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping("/{userId}")
    public OrderReadingDTOCustomer placeOrder(@PathVariable int userId, @Valid @RequestBody OrderCreationDTO entity) {
        // TODO: process POST request
        return null;
    }

    @GetMapping("/{userId}/{orderId}")
    public OrderReadingDTOCustomer getOrder(@PathVariable int userId, @PathVariable int orderId) {
        // TODO: process GET request
        return null;
    }

    @GetMapping("/backOffice/{orderId}")
    public Order getOrder_BackOffice(@PathVariable int orderId) {
        // TODO: process GET request
        return null;
    }

    @GetMapping("/available")
    public List<OrderReadingDTOBiker> getAvailableOrders() {
        // TODO: process GET request
        return null;
    }

    @GetMapping("/backOffice/available")
    public List<Order> getAvailableOrders_BackOffice() {
        // TODO: process GET request
        return null;
    }

    @GetMapping("stores/{storeId}")
    public List<Order> getOrdersByStore(@PathVariable int storeId) {
        // TODO: process GET request
        return null;
    }

    @GetMapping("/backOffice/bikers/{bikerId}")
    // Accessed by Back Office User
    public List<Order> getOrdersByBiker() {
        // TODO: process GET request
        return null;
    }

    @PutMapping("/rate/{userId}/{orderId}")
    public SuccessResponse RateDelivery(@PathVariable int userId, @PathVariable int orderId,
            @Valid @RequestBody FeedBackCreationDTO entity) {
        // TODO: process PUT request
        return null;
    }

    @PutMapping("/{id}/{orderId}")
    public SuccessResponse UpdateStatus(@PathVariable int orderId, @RequestBody StatusCreationDTO entity) {
        // TODO: process PUT request
        return null;
    }

    @PutMapping("/backOffice/{orderId}")
    public SuccessResponse UpdateStatus_BackOffice(@PathVariable int orderId,
            @RequestBody StatusCreationDTO entity) {
        // TODO: process PUT request
        return null;
    }

    @PutMapping("/assign/{bikerId}/{orderId}")
    public SuccessResponse AssignDelivery(@PathVariable int bikerId, @PathVariable int orderId) {
        // TODO: process PUT request
        return null;
    }

    @DeleteMapping("/backOffice/{orderId}")
    public SuccessResponse deleteOrder(@PathVariable int orderId) {
        // TODO: process DELETE request
        return null;
    }

}
