package com.Gateway_Service.Gateway_Service.dataclass.parse;

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
