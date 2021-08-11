package com.Analyse_Service.Analyse_Service.response;

import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;

import java.util.ArrayList;

public class AnalyseDataResponse {
    ArrayList<ArrayList> pattenList;
    ArrayList<ArrayList> relationshipList;
    ArrayList<ArrayList> predictionList;

    public AnalyseDataResponse(ArrayList<ArrayList> pattenList,
                               ArrayList<ArrayList> relationshipList,
                               ArrayList<ArrayList> predictionList){
        this.pattenList = pattenList;
        this.relationshipList = relationshipList;
        this.predictionList = predictionList;
    }

    public ArrayList<ArrayList> getPattenList(){
        return pattenList;
    }

    public ArrayList<ArrayList> getRelationshipList(){
        return relationshipList;
    }

    public ArrayList<ArrayList> getPredictionList(){
        return predictionList;
    }
}
