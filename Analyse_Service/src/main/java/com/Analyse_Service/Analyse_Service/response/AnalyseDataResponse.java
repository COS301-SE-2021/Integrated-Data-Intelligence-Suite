package com.Analyse_Service.Analyse_Service.response;

import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;

import java.util.ArrayList;

public class AnalyseDataResponse {
    public ArrayList<ArrayList> patternList;
    public ArrayList<ArrayList> relationshipList;
    public ArrayList<ArrayList> predictionList;
    public ArrayList<ArrayList> trendList;
    public ArrayList<ArrayList> anomalyList;

    public AnalyseDataResponse(ArrayList<ArrayList> pattenList,
                               ArrayList<ArrayList> relationshipList,
                               ArrayList<ArrayList> predictionList,
                               ArrayList<ArrayList> trendList,
                               ArrayList<ArrayList> anomalyList){
        this.patternList = pattenList;
        this.relationshipList = relationshipList;
        this.predictionList = predictionList;
        this.trendList = trendList;
        this.anomalyList = anomalyList;
    }

    public ArrayList<ArrayList> getPattenList(){
        return patternList;
    }

    public ArrayList<ArrayList> getRelationshipList(){
        return relationshipList;
    }

    public ArrayList<ArrayList> getPredictionList(){
        return predictionList;
    }

    public ArrayList<ArrayList> getTrendList(){
        return trendList;
    }

    public ArrayList<ArrayList> getAnomalyList(){
        return anomalyList;
    }
}
