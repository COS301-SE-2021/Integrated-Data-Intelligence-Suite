package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class TrainFindAnomaliesResponse {
    ArrayList<String> pattenList;

    public TrainFindAnomaliesResponse (ArrayList<String> pattenList){
        this.pattenList = pattenList;
    }

    public ArrayList<String> getPattenList(){
        return this.pattenList;
    }
}
