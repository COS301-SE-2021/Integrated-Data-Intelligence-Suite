package com.Analyse_Service.Analyse_Service.response;

import com.Analyse_Service.Analyse_Service.dataclass.TweetWithSentiment;

public class FindSentimentResponse {
    TweetWithSentiment sentiment;

    public FindSentimentResponse(TweetWithSentiment sentiment){
        this.sentiment = sentiment;
    }

    public TweetWithSentiment getSentiment(){
        return this.sentiment;
    }
}
