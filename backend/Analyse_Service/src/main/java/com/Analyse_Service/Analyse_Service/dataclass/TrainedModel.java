package com.Analyse_Service.Analyse_Service.dataclass;

public class TrainedModel {

    private String modelName;

    private String modelId;

    private AIType type;

    private double accuracy;

    private String runId;

    public TrainedModel(){

    }

    public TrainedModel(String modelId, double accuracy, String runId, String modelName){
        this.modelId = modelId;
        this.accuracy = accuracy;
        this.runId = runId;
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelId(){
        return this.modelId;
    }

    public AIType getType(){
        return this.type;
    }

    public double getAccuracy(){
        return this.accuracy;
    }

    public String getRunId() {
        return runId;
    }


    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public void setType(AIType type){
        this.type = type;
    }

    public void setAccuracy(float accuracy){
        this.accuracy = accuracy;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }
}
