package com.Gateway_Service.Gateway_Service.dataclass;


import java.util.List;

public class GetUserResponse {
    String message;
    boolean success;
    List<User> user;

    public GetUserResponse() {
    }

    public GetUserResponse(String message, boolean success, List<User> user) {
        this.message = message;
        this.success = success;
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<User> getUser() {
        return user;
    }
}
