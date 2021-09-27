package com.Gateway_Service.Gateway_Service.dataclass.parse;


import java.util.ArrayList;

public class ParseImportedDataResponse {
    ArrayList<ParsedData> dataList;
    ArrayList<ParsedArticle> articleList;
    private boolean success;
    private String message;

    boolean fallback = false;
    String fallbackMessage = "";

    public ParseImportedDataResponse(){

    }

    public ParseImportedDataResponse(boolean success, String message, ArrayList<ParsedData> dataList, ArrayList<ParsedArticle> articleList) {
        this.success = success;
        this.message = message;
        this.dataList = dataList;
        this.articleList = articleList;
    }

    public ArrayList<ParsedData> getDataList(){
        return dataList;
    }

    public ArrayList<ParsedArticle> getArticleList() {
        return articleList;
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
