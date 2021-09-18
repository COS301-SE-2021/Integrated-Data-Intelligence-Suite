package com.Gateway_Service.Gateway_Service.dataclass;

public class ChangeUserResponse {
    private String message;
    private boolean success;

    public ChangeUserResponse() {

    }

    public ChangeUserResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

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
