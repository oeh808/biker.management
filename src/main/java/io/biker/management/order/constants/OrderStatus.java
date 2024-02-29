package io.biker.management.order.constants;

public class OrderStatus {
    public static String AWAITING_APPROVAL = "Order is awaiting aproval";
    public static String PREPARING = "Order is being prepared";
    public static String WAITING_FOR_PICK_UP = "Order is awaiting pick up";
    public static String EN_ROUTE = "Order is on the way";
    public static String DELIVERED = "Order has been delivered";

    public static String[] statuses = new String[] { AWAITING_APPROVAL, PREPARING,
            WAITING_FOR_PICK_UP, EN_ROUTE, DELIVERED };
}
