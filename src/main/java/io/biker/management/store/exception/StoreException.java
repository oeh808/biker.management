package io.biker.management.store.exception;

public class StoreException extends RuntimeException {
    private String message;

    public StoreException(String message) {
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
