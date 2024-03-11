package io.biker.management.orderHistory.controller;

import org.springframework.web.bind.annotation.RestController;

import io.biker.management.constants.response.Responses;
import io.biker.management.errorHandling.responses.SuccessResponse;
import io.biker.management.orderHistory.dtos.OrderHistoryCreationDTO;
import io.biker.management.orderHistory.dtos.OrderHistoryReadingDTO;
import io.biker.management.orderHistory.entity.OrderHistory;
import io.biker.management.orderHistory.mapper.OrderHistoryMapper;
import io.biker.management.orderHistory.service.OrderHistoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Log4j2
@RestController
@Tag(name = "Orders History", description = "Controller for handling mappings for history of delivery orders")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/orders/history")
// FIXME: Implement method security
// FIXME: Add swagger annotations
public class OrderHistoryController {
    private OrderHistoryService orderHistoryService;
    private OrderHistoryMapper orderHistoryMapper;

    public OrderHistoryController(OrderHistoryService orderHistoryService, OrderHistoryMapper orderHistoryMapper) {
        this.orderHistoryService = orderHistoryService;
        this.orderHistoryMapper = orderHistoryMapper;
    }

    @PostMapping("/{orderId}")
    public OrderHistoryReadingDTO createOrderHistory(@PathVariable int orderId,
            @Valid @RequestBody OrderHistoryCreationDTO dto) {
        log.info("Recieved: PUT request to /orders/history/" + orderId);

        OrderHistory orderHistory = orderHistoryService.createOrderHistory(orderId, orderHistoryMapper.toDate(dto));

        return orderHistoryMapper.toDto(orderHistory);
    }

    @GetMapping()
    public List<OrderHistoryReadingDTO> getAllOrderHistory() {
        log.info("Recieved: GET request to /orders/history");

        return orderHistoryMapper.toDtos(orderHistoryService.getAllOrderHistories());
    }

    @GetMapping("/{orderId}")
    public List<OrderHistoryReadingDTO> getOrderHistoriesByOrder(@PathVariable int orderId) {
        log.info("Recieved: GET request to /orders/history/" + orderId);

        return orderHistoryMapper.toDtos(orderHistoryService.getOrderHistoriesByOrder(orderId));
    }

    @GetMapping("/{orderId}/{id}")
    public OrderHistoryReadingDTO getSingleOrderHistory(@PathVariable int orderId, @PathVariable int id) {
        log.info("Recieved: GET request to /orders/history/" + orderId + "/" + id);

        return orderHistoryMapper.toDto(orderHistoryService.getSingleOrderHistory(orderId, id));
    }

    @DeleteMapping("/{orderId}/{id}")
    public ResponseEntity<SuccessResponse> deleteOrderHistory(@PathVariable int orderId, @PathVariable int id) {
        log.info("Recieved: DELETE request to /orders/history/" + orderId + "/" + id);

        orderHistoryService.deleteOrderHistory(orderId, id);

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(Responses.ORDER_HISTORY_DELETED),
                HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<SuccessResponse> deleteOrderHistoriesByOrder(@PathVariable int orderId) {
        log.info("Recieved: DELETE request to /orders/history/" + orderId);

        orderHistoryService.deleteOrderHistoriesByOrder(orderId);

        return new ResponseEntity<SuccessResponse>(
                new SuccessResponse(Responses.ORDER_HISTORIES_DELETED(orderId)),
                HttpStatus.ACCEPTED);
    }
}
