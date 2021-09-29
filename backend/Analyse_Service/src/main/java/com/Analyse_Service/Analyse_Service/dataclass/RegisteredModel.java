package com.Analyse_Service.Analyse_Service.dataclass;

public class RegisteredModel {

    private String modelId;

    private AIType type;

    private double accuracy;

    public RegisteredModel(String modelId, double accuracy){
        this.modelId = modelId;
        this.accuracy = accuracy;
    }

    public String getModelId(){
        return this.modelId;
    }

    public void setType(AIType type){
        this.type = type;
    }

    public void setAccuracy(float accuracy){
        this.accuracy = accuracy;
    }

    public AIType getType(){
        return this.type;
    }

    public double getAccuracy(){
        return this.accuracy;
    }
}
