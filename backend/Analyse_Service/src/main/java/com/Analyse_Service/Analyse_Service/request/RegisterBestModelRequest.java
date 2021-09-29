package com.Analyse_Service.Analyse_Service.request;

import com.Analyse_Service.Analyse_Service.dataclass.TrainedModel;

import java.util.ArrayList;

public class RegisterBestModelRequest {

    private ArrayList<TrainedModel> modelList = new ArrayList<>();

    public RegisterBestModelRequest(ArrayList<TrainedModel> modelList){
        this.modelList = modelList;
    }

    public ArrayList<TrainedModel> getModelList(){
        return modelList;
    }
}
