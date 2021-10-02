package com.Gateway_Service.Gateway_Service.dataclass.user;

public class GetUserReportsRequest {
    /**
     * This is the id of the user
     */
    private String id;

    public GetUserReportsRequest() {

    }

    public GetUserReportsRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
