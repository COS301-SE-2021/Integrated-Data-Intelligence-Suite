package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class TrainFindPatternRequest {
    ArrayList<ArrayList> dataList;

    public TrainFindPatternRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
