package com.Parse_Service.Parse_Service.response;

import com.Parse_Service.Parse_Service.dataclass.ParsedArticle;
import com.Parse_Service.Parse_Service.dataclass.ParsedData;

import java.util.ArrayList;

public class ParseImportedDataResponse {
    ArrayList<ParsedData> dataList;
    ArrayList<ParsedArticle> articleList;

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
}
