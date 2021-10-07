package com.Analyse_Service.Analyse_Service.request;

import com.Analyse_Service.Analyse_Service.dataclass.TrainedModel;

import java.util.ArrayList;

public class RegisterUserBestModelRequest {

    private String modelName;

    private ArrayList<TrainedModel> modelList = new ArrayList<>();

    public RegisterUserBestModelRequest(ArrayList<TrainedModel> modelList, String modelName){
        this.modelList = modelList;
        this.modelName = modelName;
    }

    public ArrayList<TrainedModel> getModelList(){
        return modelList;
    }

    public String getModelName() {
        return modelName;
    }
}
