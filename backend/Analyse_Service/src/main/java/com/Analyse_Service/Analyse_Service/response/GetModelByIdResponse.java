package com.Analyse_Service.Analyse_Service.response;

public class GetModelByIdResponse {

    private String modelName;
    private String modelID;

    private String modelAccuracy;

    private boolean isModelDefault;


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
}
