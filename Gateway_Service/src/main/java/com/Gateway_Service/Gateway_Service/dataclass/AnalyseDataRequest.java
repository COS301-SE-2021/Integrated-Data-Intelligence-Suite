package com.Gateway_Service.Gateway_Service.dataclass;

import com.Parse_Service.Parse_Service.dataclass.ParsedData;

import java.util.ArrayList;

public class AnalyseDataRequest {
    ArrayList<ParsedData> dataList;

    public AnalyseDataRequest(){

    }

    public AnalyseDataRequest(ArrayList<ParsedData> dataList){
        this.dataList = dataList;
    }




    public ArrayList<ParsedData> getDataList(){
        return this.dataList;
    }
}
