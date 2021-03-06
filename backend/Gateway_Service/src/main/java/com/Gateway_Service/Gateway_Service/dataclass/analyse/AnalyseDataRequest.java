package com.Gateway_Service.Gateway_Service.dataclass.analyse;



import com.Gateway_Service.Gateway_Service.dataclass.parse.ParsedArticle;
import com.Gateway_Service.Gateway_Service.dataclass.parse.ParsedData;

import java.util.ArrayList;

public class AnalyseDataRequest {
    ArrayList<ParsedData> dataList;
    ArrayList<ParsedArticle> articleList;

    public AnalyseDataRequest(){

    }

    public AnalyseDataRequest(ArrayList<ParsedData> dataList, ArrayList<ParsedArticle> articleList){
        this.dataList = dataList;
        this.articleList = articleList;
    }


    public ArrayList<ParsedData> getDataList(){
        return this.dataList;
    }

    public ArrayList<ParsedArticle> getArticleList(){
        return this.articleList;
    }
}
