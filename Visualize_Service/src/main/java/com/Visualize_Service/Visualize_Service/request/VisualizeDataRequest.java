package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class VisualizeDataRequest {
    public ArrayList<ArrayList> patternList;
    public ArrayList<ArrayList> relationshipList;
    public ArrayList<ArrayList> predictionList;
    public ArrayList<ArrayList> trendList;
    public ArrayList<String> anomalyList;

    public VisualizeDataRequest(ArrayList<ArrayList> patternList,
                                ArrayList<ArrayList> relationshipList,
                                ArrayList<ArrayList> predictionList,
                                ArrayList<ArrayList> trendList,
                                ArrayList<String> anomalyList){
        this.patternList = patternList;
        this.relationshipList = relationshipList;
        this.predictionList = predictionList;
        this.trendList = trendList;
        this.anomalyList = anomalyList;
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


}
