package com.User_Service.User_Service.request;

public class GetCurrentUserRequest {
    private String id;

    public GetCurrentUserRequest() {

    }

    public GetCurrentUserRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
