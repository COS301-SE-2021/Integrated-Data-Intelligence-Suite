package com.Analyse_Service.Analyse_Service.request;

import com.Analyse_Service.Analyse_Service.dataclass.RegisteredModel;

public class SaveAIModelRequest {

    RegisteredModel saveRegisteredModel;

    public SaveAIModelRequest(RegisteredModel saveRegisteredModel){
        this.saveRegisteredModel = saveRegisteredModel;
    }

    public RegisteredModel getSaveAIModel() {
        return saveRegisteredModel;
    }
}
