package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class FindPatternResponse {
    ArrayList<ArrayList> pattenList;

    public FindPatternResponse(ArrayList<ArrayList> pattenList){
        this.pattenList = pattenList;
    }

    public ArrayList<ArrayList> getPattenList(){
        return this.pattenList;
    }
}
