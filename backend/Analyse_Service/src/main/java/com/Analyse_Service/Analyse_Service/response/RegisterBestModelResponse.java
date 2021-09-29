package com.Analyse_Service.Analyse_Service.response;

import com.Analyse_Service.Analyse_Service.dataclass.TrainedModel;

public class RegisterBestModelResponse {

    TrainedModel bestModel;

    public RegisterBestModelResponse(TrainedModel bestModel){
        this.bestModel = bestModel;
    }

    public TrainedModel getBestModel(){
        return bestModel;
    }

}
