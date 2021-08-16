package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindTrendsRequest {
    ArrayList<ArrayList> dataList;

    public FindTrendsRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
