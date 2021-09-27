package com.Gateway_Service.Gateway_Service.dataclass.impor;

import java.util.ArrayList;

public class ImportDataResponse {
    ArrayList<ImportedData> list;
    private boolean success;
    private String message;

    boolean fallback = false;
    String fallbackMessage = "";

    public ImportDataResponse() {

    }

    public ImportDataResponse(boolean success, String message, ArrayList<ImportedData> list) {
        this.success = success;
        this.message = message;
        this.list = list;
    }

    public ArrayList<ImportedData> getList() {
        return list;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
