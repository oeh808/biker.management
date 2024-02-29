package io.biker.management.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.auth.response.Responses;
import io.biker.management.error_handling.responses.SuccessResponse;
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
    public OrderReadingDTOCustomer placeOrder(@PathVariable int userId, @Valid @RequestBody OrderCreationDTO dto) {
        // FIXME: Implement method security
        Order order = orderService.createOrder(userId, orderMapper.toOrder(dto));

        return orderMapper.toDtoForCustomer(order);
    }

    @GetMapping("/{userId}/{orderId}")
    public OrderReadingDTOCustomer getOrder(@PathVariable int userId, @PathVariable int orderId) {
        // FIXME: Implement method security
        return orderMapper.toDtoForCustomer(orderService.getOrder(userId, orderId));
    }

    @GetMapping("/backOffice/{orderId}")
    public Order getOrder_BackOffice(@PathVariable int orderId) {
        // FIXME: Implement method security
        return orderService.getOrder_BackOffice(orderId);
    }

    @GetMapping("/available")
    public List<OrderReadingDTOBiker> getAvailableOrders() {
        // FIXME: Implement method security
        return orderMapper.toDtosForBiker(orderService.getAvailableOrders());
    }

    @GetMapping("/backOffice/available")
    public List<Order> getAvailableOrders_BackOffice() {
        // FIXME: Implement method security
        return orderService.getAvailableOrders();
    }

    @GetMapping("stores/{storeId}")
    public List<Order> getOrdersByStore(@PathVariable int storeId) {
        // FIXME: Implement method security
        return orderService.getOrdersByStore(storeId);
    }

    @GetMapping("/backOffice/bikers/{bikerId}")
    // Accessed by Back Office User
    public List<Order> getOrdersByBiker(@PathVariable int bikerId) {
        // FIXME: Implement method security
        return orderService.getOrdersByBiker(bikerId);
    }

    @PutMapping("/rate/{customerId}/{orderId}")
    public SuccessResponse rateDelivery(@PathVariable int customerId, @PathVariable int orderId,
            @Valid @RequestBody FeedBackCreationDTO dto) {
        // FIXME: Implement method security
        orderService.rateOrder(customerId, orderId);

        SuccessResponse successResponse = new SuccessResponse(Responses.FEEDBACK_ADDED);
        return successResponse;
    }

    @PutMapping("/{id}/{orderId}")
    public SuccessResponse updateStatus(@PathVariable int orderId, @Valid @RequestBody StatusCreationDTO dto) {
        // FIXME: Implement method security
        orderService.updateOrderStatus(orderId, orderId, orderMapper.toStatus(dto));

        SuccessResponse successResponse = new SuccessResponse(Responses.STATUS_UPDATED);
        return successResponse;
    }

    @PutMapping("/backOffice/{orderId}")
    public SuccessResponse updateStatus_BackOffice(@PathVariable int orderId,
            @Valid @RequestBody StatusCreationDTO dto) {
        // FIXME: Implement method security
        orderService.updateOrderStatus_BackOffice(orderId, orderMapper.toStatus(dto));

        SuccessResponse successResponse = new SuccessResponse(Responses.STATUS_UPDATED);
        return successResponse;
    }

    @PutMapping("/accept/{bikerId}/{orderId}")
    public SuccessResponse acceptDelivery(@PathVariable int bikerId, @PathVariable int orderId) {
        // FIXME: Implement method security
        orderService.assignDelivery(bikerId, orderId);

        SuccessResponse successResponse = new SuccessResponse(Responses.ORDER_ASSIGNED(bikerId, orderId));
        return successResponse;
    }

    @PutMapping("/assign/{bikerId}/{orderId}")
    public SuccessResponse assignDelivery(@PathVariable int bikerId, @PathVariable int orderId) {
        // FIXME: Implement method security
        orderService.assignDelivery(bikerId, orderId);

        SuccessResponse successResponse = new SuccessResponse(Responses.ORDER_ASSIGNED(bikerId, orderId));
        return successResponse;
    }

    @DeleteMapping("/backOffice/{orderId}")
    public SuccessResponse deleteOrder(@PathVariable int orderId) {
        // FIXME: Implement method security
        orderService.deleteOrder(orderId);

        SuccessResponse successResponse = new SuccessResponse(Responses.ORDER_DELETED);
        return successResponse;
    }

}
