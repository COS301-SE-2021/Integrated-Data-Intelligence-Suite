package com.User_Service.User_Service.response;

public class RequestAdminResponse {
    private boolean success;
    private String message;

    public RequestAdminResponse() {

    }

    public RequestAdminResponse(boolean success, String message) {
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
