package com.Gateway_Service.Gateway_Service.dataclass.parse;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedArticle;
import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;

import java.util.ArrayList;

public class ParseImportedDataResponse {
    ArrayList<ParsedData> dataList;
    ArrayList<ParsedArticle> articleList;

    boolean fallback = false;
    String fallbackMessage = "";

    public ParseImportedDataResponse(){

    }


    public ParseImportedDataResponse(ArrayList<ParsedData> dataList, ArrayList<ParsedArticle> articleList){
        this.dataList = dataList;
        this.articleList = articleList;
    }



    public ArrayList<ParsedData> getDataList(){
        return dataList;
    }

    public ArrayList<ParsedArticle> getArticleList() {
        return articleList;
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
