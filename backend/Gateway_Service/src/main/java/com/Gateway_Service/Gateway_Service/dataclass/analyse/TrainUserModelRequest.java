package com.Gateway_Service.Gateway_Service.dataclass.analyse;




import com.Gateway_Service.Gateway_Service.dataclass.parse.ParsedData;
import com.Gateway_Service.Gateway_Service.dataclass.parse.ParsedTrainingData;

import java.util.ArrayList;

public class TrainUserModelRequest {
    private String modelName;

    ArrayList<ParsedTrainingData> dataList;

    public TrainUserModelRequest(){

    }

    public TrainUserModelRequest(String modelName , ArrayList<ParsedTrainingData> dataList){
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
