package io.biker.management.errorHandling.responses;

public abstract class CustomResponse {
    private String message;

    public CustomResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
