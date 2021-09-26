package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindPatternRequest {
    ArrayList<ArrayList> dataList,articleList;


    public FindPatternRequest(ArrayList<ArrayList> dataList,ArrayList<ArrayList> articleList){
        this.dataList = dataList;
        this.articleList = articleList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }

    public ArrayList<ArrayList> getArticleList(){
        return articleList;
    }
}
