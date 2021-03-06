package com.Gateway_Service.Gateway_Service.dataclass.impor;

public class AddAPISourceResponse {
    private boolean success;
    private String message;

    public AddAPISourceResponse() {

    }

    public AddAPISourceResponse(boolean success, String message) {
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
