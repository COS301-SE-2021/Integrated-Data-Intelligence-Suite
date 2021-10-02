package com.Analyse_Service.Analyse_Service.response;

public class GetModelByIdResponse {

    private String modelName;
    private String modelId;

    private String modelAccuracy;


    public GetModelByIdResponse(){

    }

    public GetModelByIdResponse(String modelName, String modelId, String modelAccuracy){
        this.modelName = modelName;
        this.modelId = modelId;
        this.modelAccuracy = modelAccuracy;
    }


    public String getModelName(){
        return modelName;
    }

    public String getModelId(){
        return modelId;
    }

    public String getModelAccuracy() {
        return modelAccuracy;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public void setModelAccuracy(String modelAccuracy) {
        this.modelAccuracy = modelAccuracy;
    }
}
