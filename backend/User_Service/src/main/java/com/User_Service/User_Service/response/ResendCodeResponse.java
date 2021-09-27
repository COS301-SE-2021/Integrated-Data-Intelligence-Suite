package com.User_Service.User_Service.response;

public class ResendCodeResponse {
    private String message;
    private boolean success;

    public ResendCodeResponse() {

    }

    public ResendCodeResponse(boolean success, String message) {
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
