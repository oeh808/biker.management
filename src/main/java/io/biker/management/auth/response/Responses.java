package io.biker.management.auth.response;

public class Responses {
    public static String USER_ADDED = "User added";
    public static String USER_DELETED = "User deleted";

    public static String PRODUCT_DELETED = "Product deleted";

    public static String FEEDBACK_ADDED = "Feedback added";
    public static String STATUS_UPDATED = "Status updated";

    public static String ORDER_ASSIGNED(int bikerId, int orderId) {
        return "Biker with id: " + bikerId + " has been assigned to order with id: " + orderId;
    }

    public static String ORDER_DELETED = "Order deleted";
}
