package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class FindRelationshipsResponse {
    ArrayList<ArrayList> pattenList;

    public FindRelationshipsResponse(ArrayList<ArrayList> pattenList){
        this.pattenList = pattenList;
    }

    public ArrayList<ArrayList> getPattenList(){
        return this.pattenList;
    }
}
