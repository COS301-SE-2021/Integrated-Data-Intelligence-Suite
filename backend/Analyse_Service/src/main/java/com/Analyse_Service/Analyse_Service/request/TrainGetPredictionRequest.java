package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class TrainGetPredictionRequest {
    ArrayList<ArrayList> dataList;

    String modelName;

    public TrainGetPredictionRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
        this.modelName = null;
    }

    public TrainGetPredictionRequest(ArrayList<ArrayList> dataList, String modelId){
        this.dataList = dataList;
        this.modelName = modelId;
    }

    public ArrayList<ArrayList> getDataList(){
        return dataList;
    }

    public String getModelName(){
        return modelName;
    }
}
