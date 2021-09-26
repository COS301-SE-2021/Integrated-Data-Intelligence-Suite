package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindRelationshipsRequest {
    ArrayList<ArrayList> dataList,articleList;


    public FindRelationshipsRequest(ArrayList<ArrayList> dataList,ArrayList<ArrayList> articleList){
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
