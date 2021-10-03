package com.Gateway_Service.Gateway_Service.dataclass.analyse;

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

    public String setModelId(String modelId) {
        this.modelId = modelId;
    }
}
