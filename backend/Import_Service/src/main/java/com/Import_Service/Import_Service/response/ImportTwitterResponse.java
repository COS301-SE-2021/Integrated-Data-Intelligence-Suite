package com.Import_Service.Import_Service.response;

public class ImportTwitterResponse {
    private String jsonData;

    public ImportTwitterResponse() {
    }

    public ImportTwitterResponse(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
