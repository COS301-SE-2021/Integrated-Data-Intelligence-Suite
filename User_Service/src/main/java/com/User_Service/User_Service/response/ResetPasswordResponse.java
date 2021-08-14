package com.User_Service.User_Service.response;

public class ResetPasswordResponse {
    private String message;
    private boolean success;

    public ResetPasswordResponse() {

    }

    public ResetPasswordResponse(boolean success, String message) {
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
