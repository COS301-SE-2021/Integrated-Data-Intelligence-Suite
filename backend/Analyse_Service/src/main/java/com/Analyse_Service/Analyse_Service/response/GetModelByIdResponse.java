package com.Analyse_Service.Analyse_Service.response;

public class GetModelByIdResponse {

    private String modelName;
    private String modelId;

    public GetModelByIdResponse(){

    }

    public GetModelByIdResponse(String modelName, String modelId){
        this.modelName = modelName;
        this.modelId = modelId;
    }


    public String getModelName(){
        return modelName;
    }

    public String getModelId(){
        return modelId;
    }


    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
