package com.Analyse_Service.Analyse_Service.response;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreSentence;

import java.util.ArrayList;

public class FindEntitiesResponse {
    ArrayList<ArrayList> entitiesList;

    String sentiment ;
    String[] parts_of_speech;
    String[] lemmanation;
    ArrayList<ArrayList> named_entities;



    public FindEntitiesResponse(ArrayList<ArrayList> entitiesList){
        this.entitiesList = entitiesList;
    }

    public ArrayList<ArrayList> getEntitiesList(){
        return this.entitiesList;
    }
}
