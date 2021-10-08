package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindPatternRequest {
    ArrayList<ArrayList> dataList,articleList;

    String modelId;


    public FindPatternRequest(ArrayList<ArrayList> dataList,ArrayList<ArrayList> articleList){
        this.dataList = dataList;
        this.articleList = articleList;
        this.modelId = null;
    }

    public FindPatternRequest(ArrayList<ArrayList> dataList,ArrayList<ArrayList> articleList, String modelId){
        this.dataList = dataList;
        this.articleList = articleList;
        this.modelId = modelId;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }

    public ArrayList<ArrayList> getArticleList(){
        return articleList;
    }

    public String getModelId(){
        return modelId;
    }
}
