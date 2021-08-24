package com.User_Service.User_Service.response;

public class LoginResponse {
    private String message;
    private boolean success;
    private String id;

    public LoginResponse() {

    }

    public LoginResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
        this.id = null;
    }

    public LoginResponse(String message, boolean success, String id) {
        this.message = message;
        this.success = success;
        this.id = id;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
