package io.biker.management.constants.response;

public class Responses {
    public static String USER_ADDED = "User info added";
    public static String USER_DELETED = "User info deleted";

    public static String PRODUCT_DELETED = "Product deleted";

    public static String FEEDBACK_ADDED = "Feedback added";
    public static String STATUS_UPDATED = "Status updated";
    public static String ESTIMATED_TIME_OF_ARRIVAL_UPDATED = "Estimated time of arrival updated";

    public static String ORDER_ASSIGNED(int bikerId, int orderId) {
        return "Biker with id: " + bikerId + " has been assigned to order with id: " + orderId;
    }

    public static String ORDER_DELETED = "Order deleted";

    public static String CUSTOMER_DELETED = "Customer deleted";
    public static String STORE_DELETED = "Store deleted";
    public static String BIKER_DELETED = "Biker deleted";
    public static String BACK_OFFICE_USER_DELETED = "Back Office Users deleted";

    public static String ORDER_HISTORY_DELETED = "Record of order history deleted";

    public static String ORDER_HISTORIES_DELETED(int orderId) {
        return "All records of order history for order with id = " + orderId + " have been deleted";
    }
}
