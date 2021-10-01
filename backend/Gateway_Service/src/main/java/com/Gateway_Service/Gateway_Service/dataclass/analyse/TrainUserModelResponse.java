package com.Gateway_Service.Gateway_Service.dataclass.analyse;

public class TrainUserModelResponse {
    private long trainingLength;
    private String modelName;
    private String modelId;

    boolean fallback = false;
    String fallbackMessage = "";

    public TrainUserModelResponse(){

    }

    public TrainUserModelResponse(long trainingLength, String modelName, String modelId){
        this.trainingLength = trainingLength;
        this.modelName = modelName;
        this.modelId = modelId;
    }

    public long getTrainingLength(){
        return trainingLength;
    }

    public String getModelName(){
        return modelName;
    }

    public String getModelId(){
        return modelName;
    }

    public void setTrainingLength(long trainingLength) {
        this.trainingLength = trainingLength;
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
