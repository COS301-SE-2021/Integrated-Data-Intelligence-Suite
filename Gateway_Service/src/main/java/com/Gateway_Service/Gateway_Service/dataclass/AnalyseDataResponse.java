package com.Gateway_Service.Gateway_Service.dataclass;

import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;

public class AnalyseDataResponse {
    TweetWithSentiment sentiment;

    boolean fallback = false;
    String fallbackMessage = "";

    public AnalyseDataResponse(){

    }

    public AnalyseDataResponse(TweetWithSentiment sentiment){
        this.sentiment = sentiment;
    }

    public TweetWithSentiment getSentiment(){
        return this.sentiment;
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
