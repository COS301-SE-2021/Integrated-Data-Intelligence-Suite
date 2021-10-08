package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class GetPredictionRequest {
    ArrayList<ArrayList> dataList;

    String modelId;

    public GetPredictionRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
        this.modelId = null;
    }

    public GetPredictionRequest(ArrayList<ArrayList> dataList, String modelId){
        this.dataList = dataList;
        this.modelId = modelId;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }

    public String getModelId(){
        return modelId;
    }
}
