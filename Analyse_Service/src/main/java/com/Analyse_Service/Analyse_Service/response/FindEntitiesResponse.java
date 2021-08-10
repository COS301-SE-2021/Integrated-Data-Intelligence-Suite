package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class FindEntitiesResponse {
    ArrayList<String> entitiesList;

    public FindEntitiesResponse(ArrayList<String> entitiesList){
        this.entitiesList = entitiesList;
    }

    public ArrayList<String> getEntitiesList(){
        return this.entitiesList;
    }
}
