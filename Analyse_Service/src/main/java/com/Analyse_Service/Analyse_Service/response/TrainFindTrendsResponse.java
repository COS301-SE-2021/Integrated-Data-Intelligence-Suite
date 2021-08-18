package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class TrainFindTrendsResponse {
    ArrayList<ArrayList> pattenList;

    public TrainFindTrendsResponse(ArrayList<ArrayList> pattenList){
        this.pattenList = pattenList;
    }

    public ArrayList<ArrayList> getPattenList(){
        return this.pattenList;
    }
}
