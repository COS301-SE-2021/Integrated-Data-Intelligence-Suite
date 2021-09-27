package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class TrainFindTrendsDTRequest {
    ArrayList<ArrayList> dataList;

    public TrainFindTrendsDTRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
