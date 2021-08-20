package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class TrainGetPredictionRequest {
    ArrayList<ArrayList> dataList;

    public TrainGetPredictionRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
