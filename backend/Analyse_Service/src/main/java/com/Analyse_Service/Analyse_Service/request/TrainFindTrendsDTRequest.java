package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class TrainFindTrendsDTRequest {
    ArrayList<ArrayList> dataList;

    String modelName;

    public TrainFindTrendsDTRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
        this.modelName = null;
    }

    public TrainFindTrendsDTRequest(ArrayList<ArrayList> dataList, String modelId){
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
