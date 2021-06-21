package com.Parse_Service.Parse_Service.request;

public class GetDateRequest {
    private String jsonString;

    public GetDateRequest(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
