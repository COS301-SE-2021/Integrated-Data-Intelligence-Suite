package com.Analyse_Service.Analyse_Service.response;

public class TrainModelResponse {

    private long trainingLength;

    public TrainModelResponse(){

    }

    public TrainModelResponse(long trainingLength){
        this.trainingLength = trainingLength;
    }

    public long getTrainingLength(){
        return trainingLength;
    }


}
