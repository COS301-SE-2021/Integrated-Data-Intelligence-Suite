package com.Analyse_Service.Analyse_Service.response;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreSentence;

import java.util.ArrayList;

public class FindEntitiesResponse {
    ArrayList<ArrayList> entitiesList;

    String sentiment ;
    ArrayList<String> parts_of_speech;
    ArrayList<String> lemmanation;
    ArrayList<ArrayList> named_entities;


    public FindEntitiesResponse(ArrayList<ArrayList> entitiesList){
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
        parts_of_speech =  new ArrayList<String>();
    }

    void setLemmanation(){
        lemmanation =  new ArrayList<String>();
    }

    void setNamedEntities(){
        named_entities = new ArrayList<ArrayList>();
    }
}
