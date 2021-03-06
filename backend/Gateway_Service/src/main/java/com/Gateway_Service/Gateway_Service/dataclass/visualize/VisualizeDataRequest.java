package com.Gateway_Service.Gateway_Service.dataclass.visualize;

import java.util.ArrayList;

public class VisualizeDataRequest {
    public ArrayList<ArrayList> patternList;
    public ArrayList<ArrayList> relationshipList;
    public ArrayList<ArrayList> predictionList;
    public ArrayList<ArrayList> trendList;
    public ArrayList<String> anomalyList;
    public ArrayList<ArrayList> wordList;

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
