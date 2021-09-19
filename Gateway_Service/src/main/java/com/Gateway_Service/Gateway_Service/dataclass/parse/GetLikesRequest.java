package com.Gateway_Service.Gateway_Service.dataclass.parse;

public class GetLikesRequest {
    private String jsonString;

    public GetLikesRequest(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
