package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class FindAnomaliesResponse {
    ArrayList<String> pattenList;

    public FindAnomaliesResponse (ArrayList<String> pattenList){
        this.pattenList = pattenList;
    }

    public ArrayList<String> getPattenList(){
        return this.pattenList;
    }
}
