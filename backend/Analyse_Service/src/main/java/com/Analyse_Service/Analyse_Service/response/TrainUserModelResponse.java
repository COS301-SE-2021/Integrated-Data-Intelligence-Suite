package com.Analyse_Service.Analyse_Service.response;

public class TrainUserModelResponse {

    private long trainingLength;
    private String modelName;
    private String modelId;

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
        return modelId;
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
}
