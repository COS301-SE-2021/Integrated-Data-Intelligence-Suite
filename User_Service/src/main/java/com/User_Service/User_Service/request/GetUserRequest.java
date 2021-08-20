package com.User_Service.User_Service.request;

import java.util.UUID;

public class GetUserRequest {

    UUID id;

    public GetUserRequest() {
    }

    public GetUserRequest(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


}