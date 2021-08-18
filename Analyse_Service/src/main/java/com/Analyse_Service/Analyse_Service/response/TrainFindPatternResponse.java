package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class TrainFindPatternResponse {
    ArrayList<ArrayList> pattenList;

    public TrainFindPatternResponse(ArrayList<ArrayList> pattenList){
        this.pattenList = pattenList;
    }

    public ArrayList<ArrayList> getPattenList(){
        return this.pattenList;
    }
}
