package com.Analyse_Service.Analyse_Service.request;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;

import java.util.ArrayList;

public class AnalyseDataRequest {
    ArrayList<ParsedData> dataList;

    public AnalyseDataRequest(ArrayList<ParsedData> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ParsedData> getDataList(){
        return this.dataList;
    }
}
