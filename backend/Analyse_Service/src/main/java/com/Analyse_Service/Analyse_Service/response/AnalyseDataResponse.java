package com.Analyse_Service.Analyse_Service.response;

import java.util.ArrayList;

public class AnalyseDataResponse {
    public ArrayList<ArrayList> patternList;
    public ArrayList<ArrayList> relationshipList;
    public ArrayList<ArrayList> predictionList;
    public ArrayList<ArrayList> trendList;
    public ArrayList<String> anomalyList;
    public ArrayList<ArrayList> wordList;

    public AnalyseDataResponse(){

    }

    public AnalyseDataResponse(ArrayList<ArrayList> pattenList,
                               ArrayList<ArrayList> relationshipList,
                               ArrayList<ArrayList> predictionList,
                               ArrayList<ArrayList> trendList,
                               ArrayList<String> anomalyList,
                               ArrayList<ArrayList> wordList){
        this.patternList = pattenList;
        this.relationshipList = relationshipList;
        this.predictionList = predictionList;
        this.trendList = trendList;
        this.anomalyList = anomalyList;
        this.wordList = wordList;
    }

    public ArrayList<ArrayList> getPattenList(){
        return this.patternList;
    }

    public ArrayList<ArrayList> getRelationshipList(){
        return this.relationshipList;
    }

    public ArrayList<ArrayList> getPredictionList(){
        return this.predictionList;
    }

    public ArrayList<ArrayList> getTrendList(){
        return this.trendList;
    }

    public ArrayList<String> getAnomalyList(){
        return this.anomalyList;
    }

    public ArrayList<ArrayList> getWordList(){
        return this.wordList;
    }
}
