package com.Gateway_Service.Gateway_Service.dataclass.impor;

import com.Import_Service.Import_Service.dataclass.ImportedData;

import java.util.ArrayList;

public class ImportDataResponse {
    ArrayList<ImportedData> list;

    boolean fallback = false;
    String fallbackMessage = "";

    public ImportDataResponse() {

    }

    public ImportDataResponse(ArrayList<ImportedData> list) {
        this.list = list;
    }

    public ArrayList<ImportedData> getList() {
        return list;
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
