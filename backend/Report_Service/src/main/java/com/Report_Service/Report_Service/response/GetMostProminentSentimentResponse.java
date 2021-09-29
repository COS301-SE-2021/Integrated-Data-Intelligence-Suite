package com.Report_Service.Report_Service.response;

import java.util.ArrayList;

public class GetMostProminentSentimentResponse {
    public String sentiment;

    public GetMostProminentSentimentResponse(String sentiment){
        this.sentiment = sentiment;
    }

    public String getWordGraphArray(){
        return sentiment;
    }
}
