package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class VisualizeDataRequest {
    public ArrayList<ArrayList> patternList;
    public ArrayList<ArrayList> relationshipList;
    public ArrayList<ArrayList> predictionList;
    public ArrayList<ArrayList> TrendList;
    public ArrayList<ArrayList> AnomalyList;

    public VisualizeDataRequest(ArrayList<ArrayList>patternList, ArrayList<ArrayList> relationshipList, ArrayList<ArrayList> predictionList,ArrayList<ArrayList> TrendList,ArrayList<ArrayList> AnomalyList){
        this.patternList = patternList;
        this.relationshipList = relationshipList;
        this.predictionList = predictionList;
        this.AnomalyList = AnomalyList;
        this.TrendList = TrendList;
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
        return TrendList;
    }

    public ArrayList<ArrayList> getAnomalyList(){
        return AnomalyList;
    }


}
