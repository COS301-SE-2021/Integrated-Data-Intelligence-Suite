package com.Gateway_Service.Gateway_Service.dataclass.analyse;

public class GetModelByIdResponse {

    private String modelName;
    private String modelId;

    boolean fallback = false;
    String fallbackMessage = "";

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
