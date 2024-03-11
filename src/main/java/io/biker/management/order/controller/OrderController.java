package io.biker.management.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biker.management.constants.Roles_Const;
import io.biker.management.constants.response.Responses;
import io.biker.management.customer.dtos.AddressCreationDTO;
import io.biker.management.errorHandling.responses.SuccessResponse;
import io.biker.management.order.dto.EstimatedTimeOfArrivalCreationDTO;
import io.biker.management.order.dto.FeedBackCreationDTO;
import io.biker.management.order.dto.OrderReadingDTOBiker;
import io.biker.management.order.dto.OrderReadingDTOCustomer;
import io.biker.management.order.dto.OrderReadingDTOStoreAndBackOffice;
import io.biker.management.order.dto.StatusCreationDTO;
import io.biker.management.order.entity.Order;
import io.biker.management.order.mapper.OrderMapper;
import io.biker.management.order.service.OrderService;
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
@Tag(name = "Orders", description = "Controller for handling mappings for delivery orders")
@SecurityRequirement(name = "Authorization")
@RequestMapping("/orders")
public class OrderController {
        private OrderService orderService;
        private OrderMapper orderMapper;

        public OrderController(OrderService orderService, OrderMapper orderMapper) {
                this.orderService = orderService;
                this.orderMapper = orderMapper;
        }

        @Operation(description = "POST endpoint for creating an order." +
                        "\n\n Can only be done by customers placing orders for themselves." +
                        "\n\n Returns the order created as an instance of OrderReadingDTOCustomer", summary = "Create order")
        @PostMapping("/{userId}/{productId}")
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of AddressCreationDTO")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "') or " +
                        "(hasAuthority('" + Roles_Const.CUSTOMER + "') and #userId == authentication.principal.id)")
        public OrderReadingDTOCustomer placeOrder(
                        @Parameter(in = ParameterIn.PATH, name = "userId", description = "Customer ID") @PathVariable int userId,
                        @Parameter(in = ParameterIn.PATH, name = "productId", description = "Product ID") @PathVariable int productId,
                        @Valid @RequestBody AddressCreationDTO dto) {
                log.info("Recieved: POST request to /orders/" + userId + "/" + productId);
                Order order = orderService.createOrder(userId, productId, orderMapper.toAddress(dto));

                return orderMapper.toDtoForCustomer(order);
        }

        @Operation(description = "GET endpoint for retrieving a single order by its id." +
                        "\n\n Can only be done by customers who have placed the order." +
                        "\n\n Returns the order as an instance of OrderReadingDTOCustomer", summary = "Get order (Customer)")
        @GetMapping("/{userId}/{orderId}")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "') or " +
                        "(hasAuthority('" + Roles_Const.CUSTOMER + "') and #userId == authentication.principal.id)")
        public OrderReadingDTOCustomer getOrder(
                        @Parameter(in = ParameterIn.PATH, name = "userId", description = "Customer ID") @PathVariable int userId,
                        @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID") @PathVariable int orderId) {
                log.info("Recieved: GET request to /orders/" + userId + "/" + orderId);

                return orderMapper.toDtoForCustomer(orderService.getOrder(userId, orderId));
        }

        @Operation(description = "GET endpoint for retrieving a single order by its id. (Provides more database related info in response than similair request)"
                        +
                        "\n\n Can only be done by back office users." +
                        " the order as an instance of OrderReadingDTOStoreAndBackOffice", summary = "Get order (Back office)")
        @GetMapping("/backOffice/{orderId}")
        @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
        public OrderReadingDTOStoreAndBackOffice getOrder_BackOffice(
                        @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID") @PathVariable int orderId) {
                log.info("Recieved: GET request to /orders/backOffice/" + orderId);
                return orderMapper.toDtoForStoresAndBackOffice(orderService.getOrder_BackOffice(orderId));
        }

        @Operation(description = "GET endpoint for retrieving all orders available for pick up." +
                        "\n\n Can only be done by bikers." +
                        "\n all orders that are available as a List of OrderReadingDTOBiker", summary = "Get available orders (Biker)")
        @GetMapping("/available")
        @PreAuthorize("hasAuthority('" + Roles_Const.BIKER + "')")
        public List<OrderReadingDTOBiker> getAvailableOrders() {
                log.info("Recieved: GET request to /orders/available");
                return orderMapper.toDtosForBiker(orderService.getAvailableOrders());
        }

        @Operation(description = "GET endpoint for retrieving all orders available for pick up. (Provides more database related info in response than similair request)"
                        +
                        "\n\n Can only be done by back office users." +
                        "\n\n Returns all orders that are available as a List of OrderReadingDTOStoreAndBackOffice", summary = "Get available orders (Back office)")
        @GetMapping("/backOffice/available")
        @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
        public List<OrderReadingDTOStoreAndBackOffice> getAvailableOrders_BackOffice() {
                log.info("Recieved: GET request to /orders/backOffice/available");
                return orderMapper.toDtosForStoresandBackOffice(orderService.getAvailableOrders());
        }

        @Operation(description = "GET endpoint for retrieving all orders associated with a store." +
                        "\n\n Can only be done by back office users or stores accessing their own orders." +
                        "\n\n Returns all orders associated with a store as a List of OrderReadingDTOStoreAndBackOffice", summary = "Get orders associated with store")
        @GetMapping("stores/{storeId}")
        @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "') or " +
                        "(hasAuthority('" + Roles_Const.STORE + "') and #storeId == authentication.principal.id)")
        public List<OrderReadingDTOStoreAndBackOffice> getOrdersByStore(
                        @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId) {
                log.info("Recieved: GET request to /orders/stores/" + storeId);

                return orderMapper.toDtosForStoresandBackOffice(orderService.getOrdersByStore(storeId));
        }

        @Operation(description = "GET endpoint for retrieving all orders associated with a biker." +
                        "\n\n Can only be done by back office users." +
                        "\n\n Returns all orders associated with a biker as a List of OrderReadingDTOStoreAndBackOffice", summary = "Get orders associated with biker")
        @GetMapping("/backOffice/bikers/{bikerId}")
        @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
        public List<OrderReadingDTOStoreAndBackOffice> getOrdersByBiker(
                        @Parameter(in = ParameterIn.PATH, name = "bikerId", description = "Biker ID") @PathVariable int bikerId) {
                log.info("Recieved: GET request to /orders/backOffice/bikers/" + bikerId);

                return orderMapper.toDtosForStoresandBackOffice(orderService.getOrdersByBiker(bikerId));
        }

        @Operation(description = "GET endpoint for retrieving all orders associated with a biker." +
                        "\n\n Can only be done by bikers accessing their own orders." +
                        "\n\n Returns all orders associated with a biker as a List of OrderReadingDTOBiker", summary = "Get orders associated with biker (For Bikers)")
        @GetMapping("/bikers/{bikerId}")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "') or " +
                        "(hasAuthority('" + Roles_Const.BIKER + "') and #bikerId == authentication.principal.id)")
        public List<OrderReadingDTOBiker> getOrdersByBiker_Biker(
                        @Parameter(in = ParameterIn.PATH, name = "bikerId", description = "Biker ID") @PathVariable int bikerId) {
                log.info("Recieved: GET request to /orders/bikers/" + bikerId);

                return orderMapper.toDtosForBiker(orderService.getOrdersByBiker(bikerId));
        }

        @Operation(description = "PUT endpoint for updating the estimated time of arrival of an order by a biker." +
                        "\n\n Can only be done by bikers that are associated with the order." +
                        "\n\n Returns a response as an instance of ResponseEntity<SuccessResponse>.", summary = "Update order estimated time of arrival (Biker)")
        @PutMapping("/eta/bikers/{bikerId}/{orderId}")
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of EstimatedTimeOfArrivalCreationDTO")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "') or " +
                        "(hasAuthority('" + Roles_Const.BIKER + "') and #bikerId == authentication.principal.id)")
        public ResponseEntity<SuccessResponse> updateEstimatedTimeOfArrival_Biker(
                        @Parameter(in = ParameterIn.PATH, name = "bikerId", description = "Biker ID") @PathVariable int bikerId,
                        @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID") @PathVariable int orderId,
                        @Valid @RequestBody EstimatedTimeOfArrivalCreationDTO dto) {
                log.info("Recieved: PUT request to /orders/eta/bikers/" + bikerId + "/" + orderId);

                orderService.updateOrderEstimatedTimeOfArrival_Biker(bikerId, orderId, orderMapper.toDate(dto));

                return new ResponseEntity<SuccessResponse>(
                                new SuccessResponse(Responses.ESTIMATED_TIME_OF_ARRIVAL_UPDATED),
                                HttpStatus.ACCEPTED);
        }

        @Operation(description = "PUT endpoint for updating the estimated time of arrival of an order by a store." +
                        "\n\n Can only be done by stores that are associated with the order." +
                        "\n\n Returns a response as an instance of ResponseEntity<SuccessResponse>.", summary = "Update order estimated time of arrival (Stoner)")
        @PutMapping("/eta/stores/{storeId}/{orderId}")
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of EstimatedTimeOfArrivalCreationDTO")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "') or " +
                        "(hasAuthority('" + Roles_Const.STORE + "') and #storeId == authentication.principal.id)")
        public ResponseEntity<SuccessResponse> updateEstimatedTimeOfArrival_Store(
                        @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId,
                        @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID") @PathVariable int orderId,
                        @Valid @RequestBody EstimatedTimeOfArrivalCreationDTO dto) {
                log.info("Recieved: PUT request to /orders/eta/stores/" + storeId + "/" + orderId);

                orderService.updateOrderEstimatedTimeOfArrival_Store(storeId, orderId, orderMapper.toDate(dto));

                return new ResponseEntity<SuccessResponse>(
                                new SuccessResponse(Responses.ESTIMATED_TIME_OF_ARRIVAL_UPDATED),
                                HttpStatus.ACCEPTED);
        }

        @Operation(description = "PUT endpoint for updating the estimated time of arrival of an order by a  backoffice user."
                        +
                        "\n\n Can only be done by back office users." +
                        "\n\n Returns a response as an instance of ResponseEntity<SuccessResponse>.", summary = "Update order estimated time of arrival (Back office)")
        @PutMapping("/eta/backOffice/{orderId}")
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of EstimatedTimeOfArrivalCreationDTO")
        @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
        public ResponseEntity<SuccessResponse> updateEstimatedTimeOfArrival_BackOffice(
                        @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID") @PathVariable int orderId,
                        @Valid @RequestBody EstimatedTimeOfArrivalCreationDTO dto) {
                log.info("Recieved: PUT request to /orders/eta/backOffice/" + orderId);
                orderService.updateOrderEstimatedTimeOfArrival_BackOffice(orderId, orderMapper.toDate(dto));

                return new ResponseEntity<SuccessResponse>(
                                new SuccessResponse(Responses.ESTIMATED_TIME_OF_ARRIVAL_UPDATED),
                                HttpStatus.ACCEPTED);
        }

        @Operation(description = "PUT endpoint for customers to give feedback to an order." +
                        "\n\n Can only be done by customers rating their own orders." +
                        "\n\n Returns a response as an instance of ResponseEntity<SuccessResponse>.", summary = "Set order feedback")
        @PutMapping("/rate/{userId}/{orderId}")
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of FeedBackCreationDTO")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "') or " +
                        "(hasAuthority('" + Roles_Const.CUSTOMER + "') and #userId == authentication.principal.id)")
        public ResponseEntity<SuccessResponse> rateDelivery(
                        @Parameter(in = ParameterIn.PATH, name = "userId", description = "Customer ID") @PathVariable int userId,
                        @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID") @PathVariable int orderId,
                        @Valid @RequestBody FeedBackCreationDTO dto) {
                log.info("Recieved: PUT request to /orders/rate/" + userId + "/" + orderId);
                orderService.rateOrder(userId, orderId, orderMapper.toFeedBack(dto));

                return new ResponseEntity<SuccessResponse>(
                                new SuccessResponse(Responses.FEEDBACK_ADDED),
                                HttpStatus.ACCEPTED);
        }

        @Operation(description = "PUT endpoint for updating the status of an order by a biker." +
                        "\n\n Can only be done by bikers that are associated with the order." +
                        "\n\n Returns a response as an instance of ResponseEntity<SuccessResponse>.", summary = "Update order status (Biker)")
        @PutMapping("/status/bikers/{bikerId}/{orderId}")
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of StatusCreationDTO")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "') or " +
                        "(hasAuthority('" + Roles_Const.BIKER + "') and #bikerId == authentication.principal.id)")
        public ResponseEntity<SuccessResponse> updateStatus_Biker(
                        @Parameter(in = ParameterIn.PATH, name = "bikerId", description = "Biker ID") @PathVariable int bikerId,
                        @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID") @PathVariable int orderId,
                        @Valid @RequestBody StatusCreationDTO dto) {
                log.info("Recieved: PUT request to /orders/status/bikers/" + bikerId + "/" + orderId);
                orderService.updateOrderStatus_Biker(bikerId, orderId, orderMapper.toStatus(dto));

                return new ResponseEntity<SuccessResponse>(
                                new SuccessResponse(Responses.STATUS_UPDATED),
                                HttpStatus.ACCEPTED);
        }

        @Operation(description = "PUT endpoint for updating the status of an order by a store." +
                        "\n\n Can only be done by stores that are associated with the order." +
                        "\n\n Returns a response as an instance of ResponseEntity<SuccessResponse>.", summary = "Update order status (Stoner)")
        @PutMapping("/status/stores/{storeId}/{orderId}")
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of StatusCreationDTO")
        @PreAuthorize("hasAuthority('" + Roles_Const.ADMIN + "') or " +
                        "(hasAuthority('" + Roles_Const.STORE + "') and #storeId == authentication.principal.id)")
        public ResponseEntity<SuccessResponse> updateStatus_Store(
                        @Parameter(in = ParameterIn.PATH, name = "storeId", description = "Store ID") @PathVariable int storeId,
                        @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID") @PathVariable int orderId,
                        @Valid @RequestBody StatusCreationDTO dto) {
                log.info("Recieved: PUT request to /orders/status/stores/" + storeId + "/" + orderId);
                orderService.updateOrderStatus_Store(storeId, orderId, orderMapper.toStatus(dto));

                return new ResponseEntity<SuccessResponse>(
                                new SuccessResponse(Responses.STATUS_UPDATED),
                                HttpStatus.ACCEPTED);
        }

        @Operation(description = "PUT endpoint for updating the status of an order by a back office user." +
                        "\n\n Can only be done by back office users." +
                        "\n\n Returns a response as an instance of ResponseEntity<SuccessResponse>.", summary = "Update order status (Back office)")
        @PutMapping("/status/backOffice/{orderId}")
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Must conform to required properties of StatusCreationDTO")
        @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
        public ResponseEntity<SuccessResponse> updateStatus_BackOffice(
                        @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID") @PathVariable int orderId,
                        @Valid @RequestBody StatusCreationDTO dto) {
                log.info("Recieved: PUT request to /orders/status/backOffice/" + orderId);
                orderService.updateOrderStatus_BackOffice(orderId, orderMapper.toStatus(dto));

                return new ResponseEntity<SuccessResponse>(
                                new SuccessResponse(Responses.STATUS_UPDATED),
                                HttpStatus.ACCEPTED);
        }

        @Operation(description = "PUT endpoint to assign a biker to an order." +
                        "\n\n Can only be done by back office users or bikers trying to accept an order for themselves."
                        +
                        "\n\n Returns a response as an instance of ResponseEntity<SuccessResponse>.", summary = "Assign order")
        @PutMapping("/assign/{bikerId}/{orderId}")
        @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "') or " +
                        "(hasAuthority('" + Roles_Const.BIKER + "') and #bikerId == authentication.principal.id)")
        public ResponseEntity<SuccessResponse> assignDelivery(
                        @Parameter(in = ParameterIn.PATH, name = "bikerId", description = "Biker ID") @PathVariable int bikerId,
                        @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID") @PathVariable int orderId) {
                log.info("Recieved: PUT request to /orders/assign/" + bikerId + "/" + orderId);
                orderService.assignDelivery(bikerId, orderId);

                return new ResponseEntity<SuccessResponse>(
                                new SuccessResponse(Responses.ORDER_ASSIGNED(bikerId, orderId)),
                                HttpStatus.ACCEPTED);
        }

        @Operation(description = "DELETE endpoint to delete an order." +
                        "\n\n Can only be done by back office users." +
                        "\n\n Returns a response as an instance of ResponseEntity<SuccessResponse>.", summary = "Delete order")
        @DeleteMapping("/backOffice/{orderId}")
        @PreAuthorize("hasAuthority('" + Roles_Const.BACK_OFFICE + "')")
        public ResponseEntity<SuccessResponse> deleteOrder(
                        @Parameter(in = ParameterIn.PATH, name = "orderId", description = "Order ID") @PathVariable int orderId) {
                log.info("Recieved: DELETE request to /orders/backOffice/" + orderId);
                orderService.deleteOrder(orderId);

                return new ResponseEntity<SuccessResponse>(
                                new SuccessResponse(Responses.ORDER_DELETED),
                                HttpStatus.ACCEPTED);
        }

}
