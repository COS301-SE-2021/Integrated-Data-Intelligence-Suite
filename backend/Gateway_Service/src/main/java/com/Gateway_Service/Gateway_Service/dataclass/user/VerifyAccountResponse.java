package com.Gateway_Service.Gateway_Service.dataclass.user;

public class VerifyAccountResponse {
    private String message;
    private boolean success;

    public VerifyAccountResponse() {

    }

    public VerifyAccountResponse(boolean success, String message) {
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
