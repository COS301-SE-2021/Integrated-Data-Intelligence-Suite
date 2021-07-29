package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class FindAnomaliesResponse {
    ArrayList<ArrayList> pattenList;

    public FindAnomaliesResponse (ArrayList<ArrayList> pattenList){
        this.pattenList = pattenList;
    }

    public ArrayList<ArrayList> getPattenList(){
        return this.pattenList;
    }
}
