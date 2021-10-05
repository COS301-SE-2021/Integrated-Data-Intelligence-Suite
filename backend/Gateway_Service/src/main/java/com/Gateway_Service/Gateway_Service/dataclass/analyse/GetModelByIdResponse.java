package com.Gateway_Service.Gateway_Service.dataclass.analyse;

public class GetModelByIdResponse {

    private String modelName;
    private String modelID;
    private String modelAccuracy;
    private boolean isModelDefault;

    boolean fallback = false;
    String fallbackMessage = "";


    public GetModelByIdResponse(){

    }

    public GetModelByIdResponse(String modelName, String modelID, String modelAccuracy){
        this.modelName = modelName;
        this.modelID = modelID;
        this.modelAccuracy = modelAccuracy;
        this.isModelDefault = false;
    }


    public String getModelName(){
        return modelName;
    }

    public String getModelID(){
        return modelID;
    }

    public String getModelAccuracy() {
        return modelAccuracy;
    }

    public boolean getIsModelDefault(){
        return isModelDefault;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setModelID(String modelID) {
        this.modelID = modelID;
    }

    public void setModelAccuracy(String modelAccuracy) {
        this.modelAccuracy = modelAccuracy;
    }

    public void setIsModelDefault(boolean isDefault) {
        this.isModelDefault = isDefault;
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
