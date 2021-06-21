package com.Parse_Service.Parse_Service.response;

public class GetLocationResponse {
    private String location;

    public GetLocationResponse(String location) {
        this.location = location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
