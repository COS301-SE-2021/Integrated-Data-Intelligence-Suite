package com.Parse_Service.Parse_Service.request;

public class GetLocationRequest {
    private String jsonString;

    public GetLocationRequest(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
