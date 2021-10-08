package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class GetPredictionResponse {
    ArrayList<ArrayList> pattenList;

    public GetPredictionResponse(ArrayList<ArrayList> pattenList){
        this.pattenList = pattenList;
    }

    public ArrayList<ArrayList> getPattenList(){
        return this.pattenList;
    }
}
