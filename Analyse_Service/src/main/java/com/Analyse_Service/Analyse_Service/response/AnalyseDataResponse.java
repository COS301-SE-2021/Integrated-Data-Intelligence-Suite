package com.Analyse_Service.Analyse_Service.response;

import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;

import java.util.ArrayList;

public class AnalyseDataResponse {
    TweetWithSentiment sentiment;

    public AnalyseDataResponse(TweetWithSentiment sentiment){
        this.sentiment = sentiment;
    }

    public TweetWithSentiment getSentiment(){
        return this.sentiment;
    }
}
