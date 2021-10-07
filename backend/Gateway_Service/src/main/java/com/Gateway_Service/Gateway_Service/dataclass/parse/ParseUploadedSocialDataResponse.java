package com.Gateway_Service.Gateway_Service.dataclass.parse;

import java.util.ArrayList;

public class ParseUploadedSocialDataResponse {
    ArrayList<ParsedData> socialDataList;
    private boolean success;
    private String message;

    boolean fallback = false;
    String fallbackMessage = "";

    public ParseUploadedSocialDataResponse() {

    }

    public ParseUploadedSocialDataResponse(boolean success, String message, ArrayList<ParsedData> dataList) {
        this.success = success;
        this.message = message;
        this.socialDataList = dataList;
    }

    public ArrayList<ParsedData> getSocialDataList(){
        return socialDataList;
    }

    public void setSocialDataList(ArrayList<ParsedData> socialDataList) {
        this.socialDataList = socialDataList;
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
