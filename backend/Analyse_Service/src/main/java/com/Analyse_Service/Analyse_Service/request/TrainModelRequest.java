package com.Analyse_Service.Analyse_Service.request;

public class TrainModelRequest {

    private String modelName;

    public TrainModelRequest(){

    }

    public TrainModelRequest(String modelName){
        this.modelName = modelName;
    }

    public String getModelName(){
        return modelName;
    }


}
