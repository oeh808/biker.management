package io.biker.management.order.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import io.biker.management.order.dto.FeedBackCreationDTO;
import io.biker.management.order.dto.OrderCreationDTO;
import io.biker.management.order.dto.OrderReadingDTOBiker;
import io.biker.management.order.dto.OrderReadingDTOCustomer;
import io.biker.management.order.dto.StatusCreationDTO;
import io.biker.management.order.entity.FeedBack;
import io.biker.management.order.entity.Order;

@Component
public class OrderMapper {
    // To Dto
    public OrderReadingDTOCustomer toDtoForCustomer(Order order) {
        // TODO: Implement method
        return null;
    }

    public OrderReadingDTOBiker toDtoForBiker(Order order) {
        // TODO: Implement method
        return null;
    }

    public List<OrderReadingDTOBiker> toDtosForBiker(List<Order> orders) {
        // TODO: Implement method
        return null;
    }

    // To Entity
    public Order toOrder(OrderCreationDTO dto) {
        // TODO: Implement method
        return null;
    }

    public String toStatus(StatusCreationDTO dto) {
        // TODO: Implement method
        return null;
    }

    public FeedBack toFeedBack(FeedBackCreationDTO dto) {
        // TODO: Implement method
        return null;
    }

}
