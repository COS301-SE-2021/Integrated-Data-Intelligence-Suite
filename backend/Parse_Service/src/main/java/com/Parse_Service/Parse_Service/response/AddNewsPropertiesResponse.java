package com.Parse_Service.Parse_Service.response;

public class AddNewsPropertiesResponse {
    private boolean success;
    private String message;

    public AddNewsPropertiesResponse() {

    }

    public AddNewsPropertiesResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
