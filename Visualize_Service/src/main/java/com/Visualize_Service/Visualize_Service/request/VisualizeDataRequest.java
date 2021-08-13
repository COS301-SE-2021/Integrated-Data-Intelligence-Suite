package com.Visualize_Service.Visualize_Service.request;

import java.util.ArrayList;

public class VisualizeDataRequest {
    public ArrayList<ArrayList> patternList;
    public ArrayList<ArrayList> relationshipList;
    public ArrayList<ArrayList> predictionList;

    public VisualizeDataRequest(ArrayList<ArrayList>patternList, ArrayList<ArrayList> relationshipList, ArrayList<ArrayList> predictionList){
        this.patternList = patternList;
        this.relationshipList = relationshipList;
        this.predictionList = predictionList;
    }

    public ArrayList<ArrayList> getPatternList(){
        return patternList;
    }
    public ArrayList<ArrayList>  getRelationshipList(){
        return relationshipList;    }
    public ArrayList<ArrayList> getPredictionList(){
        return predictionList;
    }

}
