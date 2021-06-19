package com.Gateway_Service.Gateway_Service.dataclass;

import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;

public class AnalyseDataResponse {
    TweetWithSentiment sentiment;

    public AnalyseDataResponse(){

    }

    public AnalyseDataResponse(TweetWithSentiment sentiment){
        this.sentiment = sentiment;
    }

    public TweetWithSentiment getSentiment(){
        return this.sentiment;
    }
}
