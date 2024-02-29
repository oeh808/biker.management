package io.biker.management.order.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import io.biker.management.customer.dtos.AddressCreationDTO;
import io.biker.management.order.constants.OrderStatus;
import io.biker.management.order.dto.FeedBackCreationDTO;
import io.biker.management.order.dto.OrderReadingDTOBiker;
import io.biker.management.order.dto.OrderReadingDTOCustomer;
import io.biker.management.order.dto.StatusCreationDTO;
import io.biker.management.order.entity.FeedBack;
import io.biker.management.order.entity.Order;
import io.biker.management.order.entity.OrderDetails;
import io.biker.management.order.exception.OrderException;
import io.biker.management.order.exception.OrderExceptionMessages;
import io.biker.management.user.Address;

@Component
public class OrderMapper {
    // To Dto
    public OrderReadingDTOCustomer toDtoForCustomer(Order order) {
        OrderDetails orderDetails = order.getOrderDetails();
        OrderReadingDTOCustomer dto = new OrderReadingDTOCustomer(order.getStatus(), order.getEta(),
                orderDetails.getProduct(), orderDetails.getPrice(), orderDetails.getVAT(), orderDetails.getTotalCost(),
                order.getBiker().getName(), orderDetails.getAddress());

        return dto;
    }

    public OrderReadingDTOBiker toDtoForBiker(Order order) {
        OrderDetails orderDetails = order.getOrderDetails();
        OrderReadingDTOBiker dto = new OrderReadingDTOBiker(order.getEta(), orderDetails.getProduct(),
                orderDetails.getPrice(), order.getCustomer().getName(), order.getCustomer().getEmail(),
                order.getCustomer().getPhoneNumber(), orderDetails.getAddress());

        return dto;
    }

    public List<OrderReadingDTOBiker> toDtosForBiker(List<Order> orders) {
        List<OrderReadingDTOBiker> dtos = new ArrayList<>();
        for (Order order : orders) {
            dtos.add(toDtoForBiker(order));
        }

        return dtos;
    }

    // To Entity
    public Address toAddress(AddressCreationDTO dto) {
        Address address = new Address(dto.street(), dto.city(), dto.state(), dto.postCode(), dto.country());

        return address;
    }

    public String toStatus(StatusCreationDTO dto) {
        // Checks if the status provided is of the valid statuses
        if (Arrays.stream(OrderStatus.statuses).anyMatch(dto.status()::equals)) {
            return dto.status();
        } else {
            throw new OrderException(OrderExceptionMessages.INVALID_STATUS);
        }
    }

    public FeedBack toFeedBack(FeedBackCreationDTO dto) {
        FeedBack feedBack = new FeedBack(dto.rating(), dto.review());
        return feedBack;
    }
}
