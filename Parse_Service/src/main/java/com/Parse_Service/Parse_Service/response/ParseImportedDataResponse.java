package com.Parse_Service.Parse_Service.response;

import com.Parse_Service.Parse_Service.dataclass.ParsedData;

import java.util.ArrayList;

public class ParseImportedDataResponse {
    ArrayList<ParsedData> dataList;

    public ParseImportedDataResponse(ArrayList<ParsedData> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ParsedData> getDataList(){
        return dataList;
    }
}
