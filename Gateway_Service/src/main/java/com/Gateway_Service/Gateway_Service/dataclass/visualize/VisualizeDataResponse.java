package com.Gateway_Service.Gateway_Service.dataclass.visualize;

import java.util.ArrayList;

public class VisualizeDataResponse {
    public ArrayList<ArrayList> outputData;

    boolean fallback = false;
    String fallbackMessage = "";

    public VisualizeDataResponse(){

    }
    public VisualizeDataResponse(ArrayList<ArrayList> outputData){
        this.outputData = outputData;
    }

    public ArrayList<ArrayList> getOutputData(){
        return outputData;
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
