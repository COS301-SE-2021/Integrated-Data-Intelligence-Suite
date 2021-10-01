package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class TrainFindAnomaliesRequest {

    ArrayList<ArrayList> dataList;

    String modelName;

    public TrainFindAnomaliesRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
        this.modelName = null;
    }

    public TrainFindAnomaliesRequest(ArrayList<ArrayList> dataList, String modelId){
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
