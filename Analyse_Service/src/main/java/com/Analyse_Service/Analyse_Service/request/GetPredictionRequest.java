package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class GetPredictionRequest {
    ArrayList<String> dataList;

    public GetPredictionRequest(ArrayList<String> dataList){
        this.dataList = dataList;
    }

    public ArrayList<String> getDataList(){
        return dataList;
    }
}
