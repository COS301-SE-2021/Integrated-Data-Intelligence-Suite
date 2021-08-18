package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class TrainFindRelationshipsResponse {
    ArrayList<ArrayList> pattenList;

    public TrainFindRelationshipsResponse(ArrayList<ArrayList> pattenList){
        this.pattenList = pattenList;
    }

    public ArrayList<ArrayList> getPattenList(){
        return this.pattenList;
    }
}
