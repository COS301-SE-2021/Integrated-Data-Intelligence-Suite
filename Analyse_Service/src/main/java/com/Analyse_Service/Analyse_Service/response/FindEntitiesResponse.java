package com.Analyse_Service.Analyse_Service.response;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreSentence;

import java.util.ArrayList;

public class FindEntitiesResponse {
    String sentiment ;
    ArrayList<ArrayList> partsOfSpeech;
    ArrayList<ArrayList> namedEntities;
    //ArrayList<ArrayList> lemmanation;

    public FindEntitiesResponse(String sentiment, ArrayList<ArrayList> partsOfSpeech, ArrayList<ArrayList> namedEntities){
        this.sentiment = sentiment;
        this.partsOfSpeech = partsOfSpeech;
        this.namedEntities = namedEntities;
    }


    String getSentiment(){
        return sentiment;
    }

    ArrayList<ArrayList>getPartsOfSpeech(){
        return partsOfSpeech;
    }

    ArrayList<ArrayList> getNamedEntities(){
        return namedEntities;
    }

    void setSentiment(String sentiment){
        this.sentiment = sentiment;
    }

    void setPartsOfSpeech(ArrayList<ArrayList> partsOfSpeech){
        this.partsOfSpeech = partsOfSpeech;
    }

    void setNamedEntities(ArrayList<ArrayList> namedEntities){
        this.namedEntities = namedEntities;
    }
}
