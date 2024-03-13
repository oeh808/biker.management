package io.biker.management.orderHistory.controller;

import org.springframework.web.bind.annotation.RestController;

import io.biker.management.constants.Roles_Const;
import io.biker.management.orderHistory.dtos.OrderHistoryReadingDTO;
import io.biker.management.orderHistory.mapper.OrderHistoryMapper;
import io.biker.management.orderHistory.service.OrderHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Log4j2
@RestController
@Tag(name = "Orders History", description = "Controller for handling mappings for history of delivery orders")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/orders/history")
public class OrderHistoryController {
        private OrderHistoryService orderHistoryService;
        private OrderHistoryMapper orderHistoryMapper;

        public OrderHistoryController(OrderHistoryService orderHistoryService, OrderHistoryMapper orderHistoryMapper) {
                this.orderHistoryService = orderHistoryService;
                this.orderHistoryMapper = orderHistoryMapper;
        }

        // @Operation(description = "POST endpoint for creating a record of order
        // history." +
        // "\n\n Can only be done by back office users." +
        // "\n\n Returns the order history record created as an instance of
        // OrderHistoryReadingDTO", summary = "Create order history")
        // @PostMapping("/{orderId}")
        // @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must
        // conform to required properties of OrderHistoryCreationDTO")
        // @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
        // public OrderHistoryReadingDTO createOrderHistory(
        // @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID")
        // @PathVariable int orderId,
        // @Valid @RequestBody OrderHistoryCreationDTO dto) {
        // log.info("Recieved: PUT request to /orders/history/" + orderId);

        // OrderHistory orderHistory = orderHistoryService.createOrderHistory(orderId,
        // orderHistoryMapper.toDate(dto));

        // return orderHistoryMapper.toDto(orderHistory);
        // }

        @Operation(description = "GET endpoint for retrieving all records of order history for all orders." +
                        "\n\n Can only be done by back office users." +
                        "\n\n Returns order history records as a List of OrderHistoryReadingDTO", summary = "Get ALL order histories")
        @GetMapping()
        @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
        public List<OrderHistoryReadingDTO> getAllOrderHistory() {
                log.info("Recieved: GET request to /orders/history");

                return orderHistoryMapper.toDtos(orderHistoryService.getAllOrderHistories());
        }

        @Operation(description = "GET endpoint for retrieving all records of order history for a specific order." +
                        "\n\n Can only be done by back office users." +
                        "\n\n Returns order history records as a List of OrderHistoryReadingDTO", summary = "Get all order histories assoiated with an order")
        @GetMapping("/{orderId}")
        @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
        public List<OrderHistoryReadingDTO> getOrderHistoriesByOrder(
                        @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID") @PathVariable int orderId) {
                log.info("Recieved: GET request to /orders/history/" + orderId);

                return orderHistoryMapper.toDtos(orderHistoryService.getOrderHistoriesByOrder(orderId));
        }

        @Operation(description = "GET endpoint for retrieving a single record of order history associated with an order."
                        +
                        "\n\n Can only be done by back office users." +
                        "\n\n Returns order history record as an instance of OrderHistoryReadingDTO", summary = "Get single order history")
        @GetMapping("/{orderId}/{id}")
        @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
        public OrderHistoryReadingDTO getSingleOrderHistory(
                        @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID") @PathVariable int orderId,
                        @Parameter(in = ParameterIn.PATH, name = "id", description = "Order History ID") @PathVariable int id) {
                log.info("Recieved: GET request to /orders/history/" + orderId + "/" + id);

                return orderHistoryMapper.toDto(orderHistoryService.getSingleOrderHistory(orderId, id));
        }

        // @Operation(description = "DELETE endpoint for deleting all records of order
        // history associated with an order." +
        // "\n\n Can only be done by back office users." +
        // "\n\n Returns a response as an instance of ResponseEntity<SuccessResponse>",
        // summary = "Delete all order histories associated with an order")
        // @DeleteMapping("/{orderId}")
        // @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
        // public ResponseEntity<SuccessResponse> deleteOrderHistoriesByOrder(
        // @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID")
        // @PathVariable int orderId) {
        // log.info("Recieved: DELETE request to /orders/history/" + orderId);

        // orderHistoryService.deleteOrderHistoriesByOrder(orderId);

        // return new ResponseEntity<SuccessResponse>(
        // new SuccessResponse(Responses.ORDER_HISTORIES_DELETED(orderId)),
        // HttpStatus.ACCEPTED);
        // }
}
