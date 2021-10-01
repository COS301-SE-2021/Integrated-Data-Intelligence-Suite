package com.Gateway_Service.Gateway_Service.dataclass.analyse;

import com.Gateway_Service.Gateway_Service.dataclass.parse.ParsedData;

import java.util.ArrayList;

public class AnalyseUserDataRequest {
    ArrayList<ParsedData> dataList;

    String modelId;

    public AnalyseUserDataRequest(){

    }

    public AnalyseUserDataRequest(ArrayList<ParsedData> dataList, String modelId){
        this.dataList = dataList;
        this.modelId = modelId;
    }


    public ArrayList<ParsedData> getDataList(){
        return this.dataList;
    }

    public String getModelId(){
        return modelId;
    }
}
