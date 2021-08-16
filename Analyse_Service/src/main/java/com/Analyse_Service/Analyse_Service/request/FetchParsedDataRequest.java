package com.Analyse_Service.Analyse_Service.request;

import com.Analyse_Service.Analyse_Service.dataclass.ParsedData;

import java.util.ArrayList;

public class FetchParsedDataRequest {
    String dataType ;

    public FetchParsedDataRequest(String dataType){
        this.dataType = dataType;
    }

    public String getDataType(){
        return this.dataType;
    }
}
