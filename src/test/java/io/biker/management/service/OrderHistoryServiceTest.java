package io.biker.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.biker.management.enums.OrderStatus;
import io.biker.management.order.entity.Order;
import io.biker.management.order.exception.OrderException;
import io.biker.management.order.exception.OrderExceptionMessages;
import io.biker.management.order.service.OrderService;
import io.biker.management.orderHistory.entity.OrderHistory;
import io.biker.management.orderHistory.exception.OrderHistoryException;
import io.biker.management.orderHistory.exception.OrderHistoryExceptionMessages;
import io.biker.management.orderHistory.repo.OrderHistoryRepo;
import io.biker.management.orderHistory.service.OrderHistoryService;
import io.biker.management.orderHistory.service.OrderHistoryServiceImpl;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class OrderHistoryServiceTest {
    @TestConfiguration
    static class ServiceTestConfig {
        @Bean
        @Autowired
        OrderHistoryService service(OrderHistoryRepo repo, OrderService orderService) {
            return new OrderHistoryServiceImpl(repo, orderService);
        }
    }

    @MockBean
    private OrderHistoryRepo repo;

    @MockBean
    private OrderService orderService;

    @Autowired
    private OrderHistoryService orderHistoryService;

    private static Order order1;
    private static Order order2;

    private static OrderHistory orderHistory1;
    private static OrderHistory orderHistory2;

    private static List<OrderHistory> orderHistories;

    @BeforeAll
    public static void setUp() {
        order1 = new Order(1, null, null, null, OrderStatus.AWAITING_APPROVAL,
                Date.valueOf("2030-04-20"), null);
        order2 = new Order(2, null, null, null, OrderStatus.DELIVERED,
                Date.valueOf("2030-09-15"), null);

        orderHistory1 = new OrderHistory(1, Date.valueOf("2030-04-17"),
                order1.getStatus(), order1.getEstimatedTimeOfArrival(), null, ZonedDateTime.now(), order1);
        orderHistory2 = new OrderHistory(2, Date.valueOf("2030-09-01"),
                order2.getStatus(), order2.getEstimatedTimeOfArrival(), null, ZonedDateTime.now(), order2);

        orderHistories = new ArrayList<>();
        orderHistories.add(orderHistory1);
        orderHistories.add(orderHistory2);
    }

    @BeforeEach
    public void setUpMocks() {
        when(orderService.getOrder(order1.getOrderId())).thenReturn(order1);
        when(orderService.getOrder(order2.getOrderId())).thenReturn(order2);
        when(orderService.getOrder(order1.getOrderId() - 1)).thenAnswer(
                (i) -> {
                    throw new OrderException(OrderExceptionMessages.ORDER_NOT_FOUND);
                });

        doAnswer(
                (i) -> {
                    return i.getArgument(0);
                }).when(repo).save(any(OrderHistory.class));

        when(repo.findAll()).thenReturn(orderHistories);
        when(repo.findByIdAndOrder(orderHistory1.getId(), order1)).thenReturn(Optional.of(orderHistory1));
        when(repo.findByIdAndOrder(orderHistory2.getId(), order1)).thenReturn(Optional.empty());
    }

    @Test
    public void createOrderHistory() {
        OrderHistory someOrderHistory = orderHistoryService.createOrderHistory(order1.getOrderId(),
                orderHistory1.getOrderCreationDate());

        assertEquals(order1, someOrderHistory.getOrder());
        assertTrue(someOrderHistory.getUpdatedAt().isAfter(orderHistory1.getUpdatedAt()));
        assertEquals(orderHistory1.getOrderCreationDate(), someOrderHistory.getOrderCreationDate());
    }

    @Test
    public void getAllOrderHistories() {
        assertEquals(orderHistories, orderHistoryService.getAllOrderHistories());
    }

    @Test
    public void getOrderHistoriesByOrder_ExistantOrder() {
        List<OrderHistory> someOrderHistories = orderHistoryService.getOrderHistoriesByOrder(order1.getOrderId());
        when(repo.findByOrder(order1)).thenReturn(someOrderHistories);

        assertEquals(someOrderHistories, orderHistoryService.getOrderHistoriesByOrder(order1.getOrderId()));
    }

    @Test
    public void getOrderHistoriesByOrder_NonExistantOrder() {
        OrderException ex = assertThrows(OrderException.class,
                () -> {
                    orderHistoryService.getOrderHistoriesByOrder(order1.getOrderId() - 1);
                });
        assertTrue(ex.getMessage().contains(OrderExceptionMessages.ORDER_NOT_FOUND));
    }

    @Test
    public void getSingleOrderHistory_Existant() {
        assertEquals(orderHistory1,
                orderHistoryService.getSingleOrderHistory(order1.getOrderId(), orderHistory1.getId()));
    }

    @Test
    public void getSingleOrderHistory_NonExistant() {
        OrderHistoryException ex = assertThrows(OrderHistoryException.class,
                () -> {
                    orderHistoryService.getSingleOrderHistory(order1.getOrderId(), orderHistory2.getId());
                });
        assertTrue(ex.getMessage()
                .contains(OrderHistoryExceptionMessages.ORDER_HISTORY_DOES_NOT_EXIST(order1.getOrderId())));
    }

    @Test
    public void deleteOrderHistory_Existant() {
        orderHistoryService.deleteOrderHistory(order1.getOrderId(), orderHistory1.getId());

        verify(repo, times(1)).deleteById(orderHistory1.getId());
    }

    @Test
    public void deleteOrderHistory_NonExistant() {
        OrderHistoryException ex = assertThrows(OrderHistoryException.class,
                () -> {
                    orderHistoryService.deleteOrderHistory(order1.getOrderId(), orderHistory2.getId());
                });
        assertTrue(ex.getMessage()
                .contains(OrderHistoryExceptionMessages.ORDER_HISTORY_DOES_NOT_EXIST(order1.getOrderId())));

        verify(repo, times(0)).deleteById(orderHistory2.getId());
    }

    @Test
    public void deleteOrderHistoriesByOrder_ExistantOrder() {
        orderHistoryService.deleteOrderHistoriesByOrder(order1.getOrderId());

        verify(repo, times(1)).deleteByOrder(order1);
    }

    @Test
    public void deleteOrderHistoriesByOrder_NonExistantOrder() {
        OrderException ex = assertThrows(OrderException.class,
                () -> {
                    orderHistoryService.deleteOrderHistoriesByOrder(order1.getOrderId() - 1);
                });
        assertTrue(ex.getMessage().contains(OrderExceptionMessages.ORDER_NOT_FOUND));

        verify(repo, times(0)).deleteByOrder(order1);
    }
}
