package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class VisualizeDataRequest {
    private ArrayList<ArrayList> patternList;
    private ArrayList<ArrayList> relationshipList;
    private ArrayList<ArrayList> predictionList;
    private ArrayList<ArrayList> trendList;
    private ArrayList<String> anomalyList;
    private ArrayList<ArrayList> wordList;

    public VisualizeDataRequest(ArrayList<ArrayList> patternList,
                                ArrayList<ArrayList> relationshipList,
                                ArrayList<ArrayList> predictionList,
                                ArrayList<ArrayList> trendList,
                                ArrayList<String> anomalyList,
                                ArrayList<ArrayList> wordList){
        this.patternList = patternList;
        this.relationshipList = relationshipList;
        this.predictionList = predictionList;
        this.trendList = trendList;
        this.anomalyList = anomalyList;
        this.wordList = wordList;

    }

    public ArrayList<ArrayList> getPatternList(){
        return patternList;
    }
    public ArrayList<ArrayList>  getRelationshipList(){
        return relationshipList;    }
    public ArrayList<ArrayList> getPredictionList(){
        return predictionList;
    }

    public ArrayList<ArrayList> getTrendList(){
        return trendList;
    }

    public ArrayList<String> getAnomalyList(){
        return anomalyList;
    }

    public ArrayList<ArrayList> getWordList(){
        return wordList;
    }


}
