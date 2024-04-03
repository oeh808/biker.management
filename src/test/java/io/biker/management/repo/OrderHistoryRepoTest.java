package io.biker.management.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import io.biker.management.enums.OrderStatus;
import io.biker.management.order.entity.Order;
import io.biker.management.order.repo.OrderRepo;
import io.biker.management.orderHistory.entity.OrderHistory;
import io.biker.management.orderHistory.repo.OrderHistoryRepo;

@ActiveProfiles("test")
@DataMongoTest
public class OrderHistoryRepoTest {
    @Autowired
    private OrderHistoryRepo orderHistoryRepo;

    @Autowired
    private OrderRepo orderRepo;

    private static Order order1;
    private static Order order2;

    private OrderHistory orderHistory1;
    private OrderHistory orderHistory2;

    @BeforeAll
    public static void setUp() {
        order1 = new Order(1, null, null, null, OrderStatus.AWAITING_APPROVAL,
                java.sql.Date.valueOf("2030-04-20"), null);
        order2 = new Order(2, null, null, null, OrderStatus.DELIVERED,
                java.sql.Date.valueOf("2030-09-15"), null);
    }

    @BeforeEach
    public void setUpForEach() {
        order1 = orderRepo.save(order1);
        order2 = orderRepo.save(order2);

        orderHistory1 = new OrderHistory(1, java.sql.Date.valueOf("2030-04-17"),
                order1.getStatus(), order1.getEstimatedTimeOfArrival(), null, Instant.now(), order1);
        orderHistory1 = orderHistoryRepo.save(orderHistory1);

        orderHistory2 = new OrderHistory(2, java.sql.Date.valueOf("2030-09-01"),
                order2.getStatus(), order2.getEstimatedTimeOfArrival(), null, Instant.now(), order2);
        orderHistory2 = orderHistoryRepo.save(orderHistory2);
    }

    @AfterEach
    public void tearDownForEach() {
        orderHistoryRepo.deleteAll();
        orderRepo.deleteAll();
    }

    @Test
    public void findByOrder() {
        List<OrderHistory> expectedList = new ArrayList<>();
        expectedList.add(orderHistory1);

        assertEquals(expectedList.get(0).getId(),
                orderHistoryRepo.findByOrder_OrderId(order1.getOrderId()).get(0).getId());
    }

    @Test
    public void findByIdAndOrder() {
        Optional<OrderHistory> opOrderHistory = orderHistoryRepo.findByIdAndOrder_OrderId(orderHistory1.getId(),
                order1.getOrderId());

        assertTrue(opOrderHistory.isPresent());
        assertEquals(orderHistory1.getId(), opOrderHistory.get().getId());
    }

    @Test
    public void deleteByOrder() {
        List<OrderHistory> expectedList = new ArrayList<>();
        expectedList.add(orderHistory2);

        orderHistoryRepo.deleteByOrder_OrderId(order1.getOrderId());

        assertEquals(expectedList.get(0).getId(), orderHistoryRepo.findAll().get(0).getId());
    }
}
