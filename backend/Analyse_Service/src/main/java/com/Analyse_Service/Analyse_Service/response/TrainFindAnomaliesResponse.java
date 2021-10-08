package com.Analyse_Service.Analyse_Service.response;

import com.Analyse_Service.Analyse_Service.dataclass.TrainedModel;

import java.util.ArrayList;

public class TrainFindAnomaliesResponse {
    ArrayList<String> pattenList;

    TrainedModel trainedModel;

    public TrainFindAnomaliesResponse (ArrayList<String> pattenList){
        this.pattenList = pattenList;
    }

    public TrainFindAnomaliesResponse (ArrayList<String> pattenList, TrainedModel trainedModel){
        this.pattenList = pattenList;
        this.trainedModel = trainedModel;
    }

    public ArrayList<String> getPattenList(){
        return this.pattenList;
    }

    public TrainedModel getTrainedModel(){
        return trainedModel;
    }
}
