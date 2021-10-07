package com.Analyse_Service.Analyse_Service.request;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;
import com.Analyse_Service.Analyse_Service.dataclass.ParsedTrainingData;

import java.util.ArrayList;

public class TrainUserModelRequest {

    private String modelName;

    ArrayList<ParsedTrainingData> dataList;

    public TrainUserModelRequest(){

    }

    public TrainUserModelRequest(String modelName, ArrayList<ParsedTrainingData> dataList){
        this.modelName = modelName;
        this.dataList = dataList;
    }

    public String getModelName(){
        return modelName;
    }

    public ArrayList<ParsedTrainingData> getDataList(){
        return this.dataList;
    }


}
