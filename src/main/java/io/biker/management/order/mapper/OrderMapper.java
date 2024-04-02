package io.biker.management.order.mapper;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.biker.management.customer.dtos.AddressCreationDTO;
import io.biker.management.enums.OrderStatus;
import io.biker.management.order.dto.EstimatedTimeOfArrivalCreationDTO;
import io.biker.management.order.dto.FeedBackCreationDTO;
import io.biker.management.order.dto.OrderReadingDTOBiker;
import io.biker.management.order.dto.OrderReadingDTOCustomer;
import io.biker.management.order.dto.OrderReadingDTOStoreAndBackOffice;
import io.biker.management.order.dto.StatusCreationDTO;
import io.biker.management.order.entity.FeedBack;
import io.biker.management.order.entity.Order;
import io.biker.management.order.entity.OrderDetails;
import io.biker.management.user.Address;

@Component
public class OrderMapper {
    // To Dto
    public OrderReadingDTOCustomer toDtoForCustomer(Order order) {
        OrderDetails orderDetails = order.getOrderDetails();
        OrderReadingDTOCustomer dto;
        if (order.getBiker() == null) {
            dto = new OrderReadingDTOCustomer(order.getStatus().toString(), order.getEstimatedTimeOfArrival(),
                    orderDetails.getProduct().getName(), orderDetails.getProduct().getPrice(), orderDetails.getVAT(),
                    orderDetails.getTotalCost(),
                    "Biker not set", orderDetails.getAddress());
        } else {
            dto = new OrderReadingDTOCustomer(order.getStatus().toString(), order.getEstimatedTimeOfArrival(),
                    orderDetails.getProduct().getName(), orderDetails.getProduct().getPrice(), orderDetails.getVAT(),
                    orderDetails.getTotalCost(),
                    order.getBiker().getName(), orderDetails.getAddress());
        }

        return dto;
    }

    public OrderReadingDTOBiker toDtoForBiker(Order order) {
        OrderDetails orderDetails = order.getOrderDetails();
        OrderReadingDTOBiker dto = new OrderReadingDTOBiker(order.getEstimatedTimeOfArrival(),
                orderDetails.getProduct().getName(),
                orderDetails.getProduct().getPrice(), order.getCustomer().getName(), order.getCustomer().getEmail(),
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

    public OrderReadingDTOStoreAndBackOffice toDtoForStoresAndBackOffice(Order order) {
        OrderDetails orderDetails = order.getOrderDetails();
        OrderReadingDTOStoreAndBackOffice dto;
        if (order.getBiker() == null) {
            dto = new OrderReadingDTOStoreAndBackOffice(order.getOrderId(),
                    order.getCustomer().getId(), order.getStore().getId(), -1, orderDetails.getProduct().getProductId(),
                    order.getStatus().toString(), order.getEstimatedTimeOfArrival(), orderDetails.getAddress(),
                    orderDetails.getTotalCost(), orderDetails.getFeedBack());
        } else {
            dto = new OrderReadingDTOStoreAndBackOffice(order.getOrderId(),
                    order.getCustomer().getId(), order.getStore().getId(), order.getBiker().getId(),
                    orderDetails.getProduct().getProductId(),
                    order.getStatus().toString(), order.getEstimatedTimeOfArrival(), orderDetails.getAddress(),
                    orderDetails.getTotalCost(), orderDetails.getFeedBack());
        }

        return dto;
    }

    public List<OrderReadingDTOStoreAndBackOffice> toDtosForStoresandBackOffice(List<Order> orders) {
        List<OrderReadingDTOStoreAndBackOffice> dtos = new ArrayList<>();
        for (Order order : orders) {
            dtos.add(toDtoForStoresAndBackOffice(order));
        }

        return dtos;
    }

    // To Entity
    public Address toAddress(AddressCreationDTO dto) {
        Address address = new Address(dto.street(), dto.city(), dto.state(), dto.postCode(), dto.country());

        return address;
    }

    public OrderStatus toStatus(StatusCreationDTO dto) {
        return dto.status();
    }

    public Date toDate(EstimatedTimeOfArrivalCreationDTO dto) {
        Date date = dto.estimatedTimeOfArrival();

        return date;
    }

    public FeedBack toFeedBack(FeedBackCreationDTO dto) {
        FeedBack feedBack = new FeedBack(dto.rating(), dto.review());
        return feedBack;
    }
}
