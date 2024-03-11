package io.biker.management.orderHistory.exception;

public class OrderHistoryException extends RuntimeException {
    private String message;

    public OrderHistoryException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
