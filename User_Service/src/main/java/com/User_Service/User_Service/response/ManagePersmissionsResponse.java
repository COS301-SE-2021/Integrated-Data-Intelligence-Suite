package com.User_Service.User_Service.response;

public class ManagePersmissionsResponse {
    private String message;
    private boolean success;

    public ManagePersmissionsResponse() {

    }

    public ManagePersmissionsResponse(String message, boolean success) {
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
