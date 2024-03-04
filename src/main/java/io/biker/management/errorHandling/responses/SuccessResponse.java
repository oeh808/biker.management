package io.biker.management.errorHandling.responses;

public class SuccessResponse extends CustomResponse {
    public SuccessResponse(String message) {
        super("SUCCESS: " + message);
    }
}
