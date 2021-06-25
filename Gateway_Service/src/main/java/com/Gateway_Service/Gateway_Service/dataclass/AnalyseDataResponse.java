package com.Gateway_Service.Gateway_Service.dataclass;

import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;

import java.util.ArrayList;

public class AnalyseDataResponse {
    ArrayList<ArrayList> pattenList;
    ArrayList<ArrayList> relationshipList;
    ArrayList<ArrayList> predictionList;

    boolean fallback = false;
    String fallbackMessage = "";


    public AnalyseDataResponse(){

    }

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

    public void setFallback(boolean fallback){
        this.fallback = fallback;
    }
    public void setFallbackMessage(String fallbackMessage){
        this.fallbackMessage = fallbackMessage;
    }

    public boolean getFallback(){
        return this.fallback;
    }

    public String getFallbackMessage(){
        return this.fallbackMessage;
    }
}
