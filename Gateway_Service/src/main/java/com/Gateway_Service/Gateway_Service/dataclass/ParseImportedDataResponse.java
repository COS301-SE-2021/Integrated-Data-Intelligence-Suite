package com.Gateway_Service.Gateway_Service.dataclass;

import com.Parse_Service.Parse_Service.dataclass.ParsedData;

import java.util.ArrayList;

public class ParseImportedDataResponse {
    ArrayList<ParsedData> dataList;

    public ParseImportedDataResponse(){

    }

    public ParseImportedDataResponse(ArrayList<ParsedData> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ParsedData> getDataList(){
        return dataList;
    }
}
