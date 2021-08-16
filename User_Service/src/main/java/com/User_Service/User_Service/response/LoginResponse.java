package com.User_Service.User_Service.response;

import java.util.UUID;

public class LoginResponse {
    private String message;
    private boolean success;
    private UUID id;

    public LoginResponse() {

    }

    public LoginResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
        this.id = null;
    }

    public LoginResponse(String message, boolean success, UUID id) {
        this.message = message;
        this.success = success;
        this.id = id;
    }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

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
