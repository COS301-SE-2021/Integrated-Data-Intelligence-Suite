package com.Analyse_Service.Analyse_Service.request;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;

import java.util.ArrayList;

public class TrainModelRequest {

    private String modelName;

    ArrayList<ParsedData> dataList;

    public TrainModelRequest(){

    }

    public TrainModelRequest(String modelName){
        this.modelName = modelName;
    }

    public String getModelName(){
        return modelName;
    }

    public ArrayList<ParsedData> getDataList(){
        return this.dataList;
    }


}
