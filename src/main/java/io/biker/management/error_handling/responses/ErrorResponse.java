package io.biker.management.error_handling.responses;

public class ErrorResponse extends CustomResponse {
    public ErrorResponse(String message) {
        super("ERROR: " + message);
    }
}
