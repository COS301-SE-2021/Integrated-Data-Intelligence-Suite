package com.Analyse_Service.Analyse_Service.response;

public class TrainUserModelResponse {

    private long trainingLength;

    public TrainUserModelResponse(){

    }

    public TrainUserModelResponse(long trainingLength){
        this.trainingLength = trainingLength;
    }

    public long getTrainingLength(){
        return trainingLength;
    }


}
