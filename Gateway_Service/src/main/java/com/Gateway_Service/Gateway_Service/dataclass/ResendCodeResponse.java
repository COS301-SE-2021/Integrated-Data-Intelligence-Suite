package com.Gateway_Service.Gateway_Service.dataclass;

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
