package com.User_Service.User_Service.request;

public class GetUserReportsRequest {
    private String id;

    public GetUserReportsRequest() {

    }

    public  GetUserReportsRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
