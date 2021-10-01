package com.Parse_Service.Parse_Service.response;

import com.Parse_Service.Parse_Service.dataclass.ParsedArticle;
import com.Parse_Service.Parse_Service.dataclass.ParsedData;

import java.util.ArrayList;

public class ParseUploadedNewsDataResponse {
    ArrayList<ParsedArticle> newsDataList;
    private boolean success;
    private String message;

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
}
