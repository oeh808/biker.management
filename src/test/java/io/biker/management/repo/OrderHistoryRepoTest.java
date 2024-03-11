package io.biker.management.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import io.biker.management.enums.OrderStatus;
import io.biker.management.order.entity.Order;
import io.biker.management.order.repo.OrderRepo;
import io.biker.management.orderHistory.entity.OrderHistory;
import io.biker.management.orderHistory.repo.OrderHistoryRepo;

@ActiveProfiles("test")
@DataJpaTest
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
                Date.valueOf("2030-04-20"), null);
        order2 = new Order(2, null, null, null, OrderStatus.DELIVERED,
                Date.valueOf("2030-09-15"), null);
    }

    @BeforeEach
    public void setUpForEach() {
        order1 = orderRepo.save(order1);
        order2 = orderRepo.save(order2);

        orderHistory1 = new OrderHistory(1, Date.valueOf("2030-04-17"),
                order1.getStatus(), order1.getEstimatedTimeOfArrival(), null, ZonedDateTime.now(), order1);
        orderHistory1 = orderHistoryRepo.save(orderHistory1);

        orderHistory2 = new OrderHistory(2, Date.valueOf("2030-09-01"),
                order2.getStatus(), order2.getEstimatedTimeOfArrival(), null, ZonedDateTime.now(), order2);
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

        assertEquals(expectedList, orderHistoryRepo.findByOrder(order1));
    }

    @Test
    public void findByIdAndOrder() {
        Optional<OrderHistory> opOrderHistory = orderHistoryRepo.findByIdAndOrder(orderHistory1.getId(), order1);

        assertTrue(opOrderHistory.isPresent());
        assertEquals(orderHistory1, opOrderHistory.get());
    }

    @Test
    public void deleteByOrder() {
        List<OrderHistory> expectedList = new ArrayList<>();
        expectedList.add(orderHistory2);

        orderHistoryRepo.deleteByOrder(order1);

        assertEquals(expectedList, orderHistoryRepo.findAll());
    }
}
