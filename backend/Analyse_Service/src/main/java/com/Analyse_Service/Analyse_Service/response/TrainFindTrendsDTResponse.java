package com.Analyse_Service.Analyse_Service.response;

import com.Analyse_Service.Analyse_Service.dataclass.TrainedModel;

import java.util.ArrayList;

public class TrainFindTrendsDTResponse {
    ArrayList<ArrayList> pattenList;

    TrainedModel trainedModel;

    public TrainFindTrendsDTResponse(ArrayList<ArrayList> pattenList){
        this.pattenList = pattenList;
    }

    public TrainFindTrendsDTResponse(ArrayList<ArrayList> pattenList, TrainedModel trainedModel){
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
