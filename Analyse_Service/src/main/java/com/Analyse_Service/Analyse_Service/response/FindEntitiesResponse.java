package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class FindEntitiesResponse {
    ArrayList<ArrayList> entitiesList;

    public FindEntitiesResponse(ArrayList<ArrayList> entitiesList){
        this.entitiesList = entitiesList;
    }

    public ArrayList<ArrayList> getEntitiesList(){
        return this.entitiesList;
    }
}
