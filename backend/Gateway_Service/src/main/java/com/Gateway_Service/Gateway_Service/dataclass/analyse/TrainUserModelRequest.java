package com.Gateway_Service.Gateway_Service.dataclass.analyse;



import com.Gateway_Service.Gateway_Service.dataclass.parse.ParsedData;

import java.util.ArrayList;

public class TrainUserModelRequest {
    private String modelName;

    ArrayList<ParsedData> dataList;

    public TrainUserModelRequest(){

    }

    public TrainUserModelRequest(String modelName , ArrayList<ParsedData> dataList){
        this.modelName = modelName;
        this.dataList = dataList;
    }

    public String getModelName(){
        return modelName;
    }

    public ArrayList<ParsedData> getDataList(){
        return this.dataList;
    }
}
