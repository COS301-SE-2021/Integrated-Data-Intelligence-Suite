package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class FindNlpPropertiesResponse {
    String sentiment ;
    ArrayList<ArrayList> namedEntities;

    public FindNlpPropertiesResponse(String sentiment,  ArrayList<ArrayList> namedEntities){
        this.sentiment = sentiment;

        this.namedEntities = namedEntities;
    }

    public void setSentiment(String sentiment){
        this.sentiment = sentiment;
    }

    public void setNamedEntities(ArrayList<ArrayList> namedEntities){
        this.namedEntities = namedEntities;
    }

    public String getSentiment(){
        return sentiment;
    }

    public ArrayList<ArrayList> getNamedEntities(){
        return namedEntities;
    }


}
