package com.Analyse_Service.Analyse_Service.response;

import com.Analyse_Service.Analyse_Service.dataclass.TrainedModel;

public class RegisterUserBestModelResponse {

    TrainedModel bestModel;

    public RegisterUserBestModelResponse(TrainedModel bestModel){
        this.bestModel = bestModel;
    }

    public TrainedModel getBestModel(){
        return bestModel;
    }

}
