package com.Analyse_Service.Analyse_Service.request;

public class FetchParsedDataRequest {
    String dataType ;

    public FetchParsedDataRequest(String dataType){
        this.dataType = dataType;
    }

    public String getDataType(){
        return this.dataType;
    }
}
