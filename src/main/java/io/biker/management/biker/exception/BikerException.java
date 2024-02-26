package io.biker.management.biker.exception;

public class BikerException extends RuntimeException {
    private String message;

    public BikerException(String message) {
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
