package com.Analyse_Service.Analyse_Service.request;

import com.Analyse_Service.Analyse_Service.dataclass.AIModel;

public class SaveAIModelRequest {

    AIModel saveAIModel;

    public SaveAIModelRequest(AIModel saveAIModel){
        this.saveAIModel = saveAIModel;
    }

    public AIModel getSaveAIModel() {
        return saveAIModel;
    }
}
