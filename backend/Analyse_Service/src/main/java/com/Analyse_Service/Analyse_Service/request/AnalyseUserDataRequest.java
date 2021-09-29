package com.Analyse_Service.Analyse_Service.request;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedArticle;
import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;

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
