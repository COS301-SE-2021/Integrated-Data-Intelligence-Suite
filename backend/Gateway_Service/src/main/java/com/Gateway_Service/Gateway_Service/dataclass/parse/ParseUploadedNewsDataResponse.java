package com.Gateway_Service.Gateway_Service.dataclass.parse;

import java.util.ArrayList;

public class ParseUploadedNewsDataResponse {
    ArrayList<ParsedArticle> newsDataList;
    private boolean success;
    private String message;

    boolean fallback = false;
    String fallbackMessage = "";

    public ParseUploadedNewsDataResponse() {

    }

    public ParseUploadedNewsDataResponse(boolean success, String message, ArrayList<ParsedArticle> dataList) {
        this.success = success;
        this.message = message;
        this.newsDataList = dataList;
    }

    public ArrayList<ParsedArticle> getNewsDataList() {
        return newsDataList;
    }

    public void setNewsDataList(ArrayList<ParsedArticle> newsDataList) {
        this.newsDataList = newsDataList;
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
