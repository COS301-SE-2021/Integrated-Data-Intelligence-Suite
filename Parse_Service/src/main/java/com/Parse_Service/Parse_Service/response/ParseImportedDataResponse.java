package com.Parse_Service.Parse_Service.response;

import com.Parse_Service.Parse_Service.dataclass.ParsedArticle;
import com.Parse_Service.Parse_Service.dataclass.ParsedData;

import java.util.ArrayList;

public class ParseImportedDataResponse {
    ArrayList<ParsedData> dataList;
    ArrayList<ParsedArticle> articleList;
    private boolean success;
    private String message;

    public ParseImportedDataResponse(){

    }

    public ParseImportedDataResponse(boolean success, String message, ArrayList<ParsedData> dataList, ArrayList<ParsedArticle> articleList){
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
}
