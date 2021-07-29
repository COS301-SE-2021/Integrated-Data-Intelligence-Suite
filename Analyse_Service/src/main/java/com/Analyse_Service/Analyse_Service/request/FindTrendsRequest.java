package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindTrendsRequest {
    ArrayList<String> dataList;

    public FindTrendsRequest(ArrayList<String> dataList){
        this.dataList = dataList;
    }

    public ArrayList<String> getDataList(){
        return dataList;
    }
}
