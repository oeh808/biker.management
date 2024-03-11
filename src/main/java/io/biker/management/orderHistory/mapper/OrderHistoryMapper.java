package io.biker.management.orderHistory.mapper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import io.biker.management.orderHistory.dtos.OrderHistoryCreationDTO;
import io.biker.management.orderHistory.dtos.OrderHistoryReadingDTO;
import io.biker.management.orderHistory.entity.OrderHistory;

@Component
public class OrderHistoryMapper {
    // To Dto
    public OrderHistoryReadingDTO toDto(OrderHistory orderHistory) {
        OrderHistoryReadingDTO dto = new OrderHistoryReadingDTO(orderHistory.getId(),
                orderHistory.getOrder().getOrderId(),
                orderHistory.getOrderCreationDate(), orderHistory.getUpdatedAt(),
                orderHistory.getEstimatedTimeOfArrival(), orderHistory.getStatus(),
                orderHistory.getBiker().getId());
        return dto;
    }

    public List<OrderHistoryReadingDTO> toDtos(List<OrderHistory> orderHistories) {
        List<OrderHistoryReadingDTO> dtos = new ArrayList<>();
        for (OrderHistory orderHistory : orderHistories) {
            dtos.add(toDto(orderHistory));
        }

        return dtos;
    }

    // To Entity
    public Date toDate(OrderHistoryCreationDTO dto) {
        return dto.orderCreationDate();
    }
}
