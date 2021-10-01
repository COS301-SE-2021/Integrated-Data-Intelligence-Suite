package com.Analyse_Service.Analyse_Service.request;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;

import java.util.ArrayList;

public class TrainUserModelRequest {

    private String modelName;

    ArrayList<ParsedData> dataList;

    public TrainUserModelRequest(){

    }

    public TrainUserModelRequest(String modelName){
        this.modelName = modelName;
    }

    public String getModelName(){
        return modelName;
    }

    public ArrayList<ParsedData> getDataList(){
        return this.dataList;
    }


}
