package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class TrainGetPredictionResponse {
    ArrayList<ArrayList> pattenList;

    public TrainGetPredictionResponse(ArrayList<ArrayList> pattenList){
        this.pattenList = pattenList;
    }

    public ArrayList<ArrayList> getPattenList(){
        return this.pattenList;
    }
}
