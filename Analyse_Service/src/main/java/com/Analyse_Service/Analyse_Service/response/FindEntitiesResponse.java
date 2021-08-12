package com.Analyse_Service.Analyse_Service.response;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreSentence;

import java.util.ArrayList;

public class FindEntitiesResponse {
    ArrayList<ArrayList> entitiesList;

    String sentiment ;
    ArrayList<String> partsOfSpeech;
    ArrayList<String> lemmanation;
    ArrayList<ArrayList> namedEntities;


    public FindEntitiesResponse(String sentiment, ArrayList<ArrayList> partsOfSpeech, ArrayList<ArrayList> namedEntities){
        this.entitiesList = entitiesList;
    }

    public ArrayList<ArrayList> getEntitiesList(){
        return this.entitiesList;
    }

    String getSentiment(){
        return "";
    }

    ArrayList<String> getPartsOfSpeech(){
        return new ArrayList<String>();
    }

    ArrayList<String> getLemmanation(){
        return new ArrayList<String>();
    }

    ArrayList<ArrayList> getNamedEntities(){
        return new ArrayList<ArrayList>();
    }

    void setSentiment(){
        sentiment = "";
    }

    void setPartsOfSpeech(){
        partsOfSpeech =  new ArrayList<String>();
    }

    void setLemmanation(){
        lemmanation =  new ArrayList<String>();
    }

    void setNamedEntities(){
        namedEntities = new ArrayList<ArrayList>();
    }
}
