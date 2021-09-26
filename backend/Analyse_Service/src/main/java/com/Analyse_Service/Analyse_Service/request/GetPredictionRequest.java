package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class GetPredictionRequest {
    ArrayList<ArrayList> dataList;

    public GetPredictionRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }
}
