package com.Analyse_Service.Analyse_Service.request;

import java.util.ArrayList;

public class FindAnomaliesRequest {
    ArrayList<ArrayList> dataList;

    String modelId;

    public FindAnomaliesRequest(ArrayList<ArrayList> dataList){
        this.dataList = dataList;
        this.modelId = null;
    }

    public FindAnomaliesRequest(ArrayList<ArrayList> dataList, String modelId){
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
