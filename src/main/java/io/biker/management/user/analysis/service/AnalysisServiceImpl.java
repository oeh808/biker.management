package io.biker.management.user.analysis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.biker.management.biker.entity.Biker;
import io.biker.management.order.entity.Order;
import io.biker.management.order.entity.OrderDetails;
import io.biker.management.order.repo.OrderRepo;
import io.biker.management.user.analysis.data.BikerAnalysis;
import io.biker.management.user.analysis.data.SystemAnalysis;
import io.biker.management.user.analysis.data.SystemReport;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    private OrderRepo orderRepo;

    public AnalysisServiceImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public SystemAnalysis getSystemAnalysis(List<Biker> bikers) {
        // Calculate average efficency of bikers
        float averageBikerEfficiency = 0;
        for (Biker biker : bikers) {
            averageBikerEfficiency += calculateDeliveryEfficiency(biker);
        }

        if (bikers.size() == 0) {
            averageBikerEfficiency = 0;
        } else {
            averageBikerEfficiency = averageBikerEfficiency / bikers.size();
        }
        averageBikerEfficiency = averageBikerEfficiency / bikers.size();
        float roundedAvgBikerEfficiency = Math.round(averageBikerEfficiency * 100.0f) / 100.0f;

        // Calculate average order rating
        List<Order> orders = orderRepo.findAll();
        int numOfOrders = orders.size();
        float averageOrderRating = 0;

        for (Order order : orders) {
            OrderDetails orderDetails = order.getOrderDetails();
            if (orderDetails != null && orderDetails.getFeedBack() != null) {
                averageOrderRating += orderDetails.getFeedBack().getRating();
            } else {
                numOfOrders--;
            }
        }

        if (orders.size() == 0) {
            averageOrderRating = 0;
        } else {
            averageOrderRating = averageOrderRating / numOfOrders;
        }
        float roundedAvgOrderRating = Math.round(averageOrderRating * 100.0f) / 100.0f;

        SystemAnalysis systemAnalysis = new SystemAnalysis(roundedAvgBikerEfficiency, roundedAvgOrderRating);
        return systemAnalysis;
    }

    @Override
    public BikerAnalysis getBikerAnalysis(Biker biker) {
        List<Order> orders = orderRepo.findByBiker(biker);
        int numOfOrders = orders.size();
        float averageOrderRating = 0;

        for (Order order : orders) {
            OrderDetails orderDetails = order.getOrderDetails();
            if (orderDetails != null && orderDetails.getFeedBack() != null) {
                averageOrderRating += orderDetails.getFeedBack().getRating();
            } else {
                numOfOrders--;
            }
        }

        if (orders.size() == 0) {
            averageOrderRating = 0;
        } else {
            averageOrderRating = averageOrderRating / numOfOrders;
        }

        float roundedAvgOrderRating = Math.round(averageOrderRating * 100.0f) / 100.0f;

        return new BikerAnalysis(roundedAvgOrderRating, calculateDeliveryEfficiency(biker));
    }

    @Override
    public SystemReport generateReport(List<Biker> bikers) {
        // Analyse system
        SystemAnalysis systemAnalysis = getSystemAnalysis(bikers);

        // Generate suggestions
        String[] suggestions = generateSuggestions();

        // Generate report
        SystemReport report = new SystemReport(systemAnalysis, suggestions);

        return report;
    }

    // Helper functions
    private float calculateDeliveryEfficiency(Biker biker) {
        /*
         * Insert code that calculates efficiency based on navigation coordinates and
         * whether the biker delivered the order within the eta or not.
         * Current code is more of a place holder.
         */
        return 3;
    }

    private String[] generateSuggestions() {
        /*
         * Insert code that analyzes the system and generates improvements to the system
         * based on insight from analysis
         */
        String[] suggestions = new String[] { "Behold! A suggestion!" };
        return suggestions;
    }
}
