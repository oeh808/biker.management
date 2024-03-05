package io.biker.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

import io.biker.management.biker.entity.Biker;
import io.biker.management.order.constants.OrderStatus;
import io.biker.management.order.entity.FeedBack;
import io.biker.management.order.entity.Order;
import io.biker.management.order.entity.OrderDetails;
import io.biker.management.order.repo.OrderRepo;
import io.biker.management.user.analysis.data.BikerAnalysis;
import io.biker.management.user.analysis.data.SystemAnalysis;
import io.biker.management.user.analysis.data.SystemReport;
import io.biker.management.user.analysis.service.AnalysisService;
import io.biker.management.user.analysis.service.AnalysisServiceImpl;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class AnalysisServiceTest {
    @TestConfiguration
    static class ServiceTestConfig {
        @Bean
        @Autowired
        AnalysisService service(OrderRepo repo) {
            return new AnalysisServiceImpl(repo);
        }
    }

    @MockBean
    private OrderRepo orderRepo;

    @Autowired
    private AnalysisService service;

    private static Biker biker;

    private static FeedBack feedBack1;
    private static FeedBack feedBack2;

    private static OrderDetails orderDetails1;
    private static OrderDetails orderDetails2;

    private static Order order1;
    private static Order order2;
    private static Order order3;

    private static List<Order> orders = new ArrayList<>();

    @BeforeAll
    public static void setUp() {
        biker = new Biker(1, "Timmy", "Timmyyy@gmail.com", "+1512 3514000", "password", null);

        feedBack1 = new FeedBack(5, "Amazing!");
        feedBack2 = new FeedBack(3, "Meh.");

        orderDetails1 = new OrderDetails(null, 0, 0, null, feedBack1);
        orderDetails2 = new OrderDetails(null, 0, 0, null, feedBack2);

        order1 = new Order(0, null, null, biker, OrderStatus.DELIVERED, Date.valueOf("2050-09-15"),
                orderDetails1);
        order2 = new Order(0, null, null, biker, OrderStatus.DELIVERED, Date.valueOf("2050-09-15"),
                orderDetails2);
        order3 = new Order(0, null, null, biker, OrderStatus.PREPARING, Date.valueOf("2050-09-15"),
                null);

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
    }

    @BeforeEach
    public void setUpMocks() {
        when(orderRepo.findByBiker(biker)).thenReturn(orders);
        when(orderRepo.findAll()).thenReturn(orders);
    }

    @Test
    public void getSystemAnalysis() {
        List<Biker> bikers = new ArrayList<>();
        bikers.add(biker);

        SystemAnalysis systemAnalysis = service.getSystemAnalysis(bikers);
        float expectedAverageRating = Math.round(((feedBack1.getRating() + feedBack2.getRating()) / 2) * 100.0f)
                / 100.0f;

        assertEquals(expectedAverageRating, systemAnalysis.getAverageOrderRating());
    }

    @Test
    public void getBikerAnalysis() {
        BikerAnalysis bikerAnalysis = service.getBikerAnalysis(biker);
        float expectedAverageRating = Math.round(((feedBack1.getRating() + feedBack2.getRating()) / 2) * 100.0f)
                / 100.0f;

        assertEquals(expectedAverageRating, bikerAnalysis.getAverageRating());
    }

    @Test
    public void generateReport() {
        List<Biker> bikers = new ArrayList<>();
        bikers.add(biker);

        SystemReport report = service.generateReport(bikers);

        float expectedAverageRating = Math.round(((feedBack1.getRating() + feedBack2.getRating()) / 2) * 100.0f)
                / 100.0f;

        assertEquals(expectedAverageRating, report.getSystemAnalysis().getAverageOrderRating());

        assertTrue(report.getSuggestions()[0].equals("Behold! A suggestion!"));
    }
}
