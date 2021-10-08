package com.Analyse_Service.Analyse_Service.response;

import com.Analyse_Service.Analyse_Service.dataclass.TrainedModel;

import java.util.ArrayList;

public class TrainGetPredictionResponse {
    ArrayList<ArrayList> pattenList;

    TrainedModel trainedModel;

    public TrainGetPredictionResponse(ArrayList<ArrayList> pattenList){
        this.pattenList = pattenList;
    }

    public TrainGetPredictionResponse(ArrayList<ArrayList> pattenList, TrainedModel trainedModel){
        this.pattenList = pattenList;
        this.trainedModel = trainedModel;
    }

    public ArrayList<ArrayList> getPattenList(){
        return this.pattenList;
    }

    public TrainedModel getTrainedModel(){
        return trainedModel;
    }
}
