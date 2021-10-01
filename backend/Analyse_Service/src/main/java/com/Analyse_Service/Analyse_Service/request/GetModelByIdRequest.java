package com.Analyse_Service.Analyse_Service.request;

import java.util.UUID;

public class GetModelByIdRequest {
    String modelId;

    public GetModelByIdRequest(){

    }

    public GetModelByIdRequest(String modelId){
        this.modelId = modelId;
    }

    public String getModelId(){
        return modelId;
    }
}
