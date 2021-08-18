package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class TrainFindAnomaliesRequest {
    ArrayList<ArrayList> dataList;

    public TrainFindAnomaliesRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
