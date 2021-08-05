package com.Gateway_Service.Gateway_Service.dataclass;

import com.Gateway_Service.Gateway_Service.dataclass.User;

import java.util.List;

public class GetAllUsersResponse {
    private List<User> users;
    private boolean success;
    private String message;

    public GetAllUsersResponse(String message, boolean success, List<User> users) {
        this.success = success;
        this.message = message;
        this.users = users;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
