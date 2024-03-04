package io.biker.management.backOffice.exception;

public class BackOfficeException extends RuntimeException {
    private String message;

    public BackOfficeException(String message) {
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
