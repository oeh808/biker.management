package io.biker.management.auth.exception;

public class CustomAuthException extends RuntimeException {
    private String message;

    public CustomAuthException(String message) {
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
