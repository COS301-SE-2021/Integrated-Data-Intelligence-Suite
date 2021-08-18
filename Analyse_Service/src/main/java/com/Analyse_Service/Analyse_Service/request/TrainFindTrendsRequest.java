package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class TrainFindTrendsRequest {
    ArrayList<ArrayList> dataList;

    public TrainFindTrendsRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
