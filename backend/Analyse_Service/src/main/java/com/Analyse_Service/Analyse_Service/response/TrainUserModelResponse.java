package com.Analyse_Service.Analyse_Service.response;

public class TrainUserModelResponse {

    private long trainingLength;
    private String modelName;
    private String modelId;

    public TrainUserModelResponse(){

    }

    public TrainUserModelResponse(long trainingLength){
        this.trainingLength = trainingLength;
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


}
