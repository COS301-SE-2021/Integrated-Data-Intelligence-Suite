package com.Parse_Service.Parse_Service.request;

public class GetTextRequest {
    private String jsonString;

    private String key;

    public GetTextRequest(String jsonString, String key) {
        this.jsonString = jsonString;
        this.key = key;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getJsonString() {
        return jsonString;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
