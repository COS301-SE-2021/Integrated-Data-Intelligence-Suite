package com.Gateway_Service.Gateway_Service.dataclass;

public class ImportTwitterResponse {
    String jsonData;

    boolean fallback = false;
    String fallbackMessage = "";

    public ImportTwitterResponse() {

    }

    public ImportTwitterResponse(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setFallback(boolean fallback){
        this.fallback = fallback;
    }
    public void setFallbackMessage(String fallbackMessage){
        this.fallbackMessage = fallbackMessage;
    }

    public boolean getFallback(){
        return this.fallback;
    }

    public String getFallbackMessage(){
        return this.fallbackMessage;
    }
}
