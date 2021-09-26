package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class FindNlpPropertiesResponse {
    String sentiment ;
    //ArrayList<ArrayList> partsOfSpeech;
    ArrayList<ArrayList> namedEntities;
    //ArrayList<ArrayList> lemmanation;

    public FindNlpPropertiesResponse(String sentiment, ArrayList<ArrayList> namedEntities){
        this.sentiment = sentiment;
        this.namedEntities = namedEntities;
        //this.partsOfSpeech = partsOfSpeech;
    }

    public void setSentiment(String sentiment){
        this.sentiment = sentiment;
    }

    /*public void setPartsOfSpeech(ArrayList<ArrayList> partsOfSpeech){
        this.partsOfSpeech = partsOfSpeech;
    }*/

    public void setNamedEntities(ArrayList<ArrayList> namedEntities){
        this.namedEntities = namedEntities;
    }

    public String getSentiment(){
        return sentiment;
    }

    /*public ArrayList<ArrayList>getPartsOfSpeech(){
        return partsOfSpeech;
    }*/

    public ArrayList<ArrayList> getNamedEntities(){
        return namedEntities;
    }


}
