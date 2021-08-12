package com.Analyse_Service.Analyse_Service.response;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreSentence;

import java.util.ArrayList;

public class FindNlpPropertiesResponse {
    String sentiment ;
    ArrayList<ArrayList> partsOfSpeech;
    ArrayList<ArrayList> namedEntities;
    //ArrayList<ArrayList> lemmanation;

    public FindNlpPropertiesResponse(String sentiment, ArrayList<ArrayList> partsOfSpeech, ArrayList<ArrayList> namedEntities){
        this.sentiment = sentiment;
        this.partsOfSpeech = partsOfSpeech;
        this.namedEntities = namedEntities;
    }

    public void setSentiment(String sentiment){
        this.sentiment = sentiment;
    }

    public void setPartsOfSpeech(ArrayList<ArrayList> partsOfSpeech){
        this.partsOfSpeech = partsOfSpeech;
    }

    public void setNamedEntities(ArrayList<ArrayList> namedEntities){
        this.namedEntities = namedEntities;
    }

    public String getSentiment(){
        return sentiment;
    }

    public ArrayList<ArrayList>getPartsOfSpeech(){
        return partsOfSpeech;
    }

    public ArrayList<ArrayList> getNamedEntities(){
        return namedEntities;
    }


}
