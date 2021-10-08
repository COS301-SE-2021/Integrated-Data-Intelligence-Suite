package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class FindTrendsResponse {
    ArrayList<ArrayList> pattenList;

    public FindTrendsResponse(ArrayList<ArrayList> pattenList){
        this.pattenList = pattenList;
    }

    public ArrayList<ArrayList> getPattenList(){
        return this.pattenList;
    }
}
