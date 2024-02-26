package io.biker.management.error_handling.responses;

public class SuccessResponse extends CustomResponse {
    public SuccessResponse(String message) {
        super("SUCCESS: " + message);
    }
}
