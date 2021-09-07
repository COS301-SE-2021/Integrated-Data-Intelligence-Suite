package com.Gateway_Service.Gateway_Service.dataclass;

import java.util.ArrayList;

public class ParseImportedDataResponse {
    ArrayList<ParsedData> dataList;

    boolean fallback = false;
    String fallbackMessage = "";

    public ParseImportedDataResponse(){

    }

    public ParseImportedDataResponse(ArrayList<ParsedData> dataList){
        this.dataList = dataList;
    }

    public ArrayList<ParsedData> getDataList(){
        return dataList;
    }

    public void setFallback(boolean fallback){
        this.fallback = fallback;
    }
    public void setFallbackMessage(String fallbackMessage){
        this.fallbackMessage = fallbackMessage;
    }

    public boolean getFallback(){
        return this.fallback;
    }

    public String getFallbackMessage(){
        return this.fallbackMessage;
    }
}
