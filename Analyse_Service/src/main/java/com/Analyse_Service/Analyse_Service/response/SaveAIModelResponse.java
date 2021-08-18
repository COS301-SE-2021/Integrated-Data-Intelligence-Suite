package com.Analyse_Service.Analyse_Service.response;

public class SaveAIModelResponse {
    boolean modelSaved;

    public SaveAIModelResponse(boolean modelSaved){
        this.modelSaved = modelSaved;
    }

    public boolean getModelSave(){
        return this.modelSaved;
    }


}
