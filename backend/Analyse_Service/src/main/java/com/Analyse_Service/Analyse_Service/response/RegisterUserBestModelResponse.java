package com.Analyse_Service.Analyse_Service.response;

import com.Analyse_Service.Analyse_Service.dataclass.TrainedModel;

public class RegisterUserBestModelResponse {

    String bestModelId;

    String bestModelName;

    public RegisterUserBestModelResponse(String bestModelId, String bestModelName){
        this.bestModelId = bestModelId;
        this.bestModelName = bestModelName;
    }

    public String getBestModelId(){
        return bestModelId;
    }

    public String getBestModelName() {
        return bestModelName;
    }

    public void setBestModelId(String bestModelId) {
        this.bestModelId = bestModelId;
    }

    public void setBestModelName(String bestModelName) {
        this.bestModelName = bestModelName;
    }
}
