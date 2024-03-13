package io.biker.management.orderHistory.exception;

public class OrderHistoryExceptionMessages {
    public static String ORDER_HISTORY_DOES_NOT_EXIST(int orderId) {
        return "Order history with the provided id associated with order with id = " + orderId + " does not exist";
    }

    public static String ORDER_HISTORY_DOES_NOT_EXIST = "There is no order history for this order";
}
