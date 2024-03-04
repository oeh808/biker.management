package io.biker.management.errorHandling.responses;

public class ErrorResponse extends CustomResponse {
    public ErrorResponse(String message) {
        super("ERROR: " + message);
    }
}
