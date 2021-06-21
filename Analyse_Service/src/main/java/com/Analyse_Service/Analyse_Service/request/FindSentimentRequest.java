package com.Analyse_Service.Analyse_Service.request;

public class FindSentimentRequest {
    String textMessage;

    public FindSentimentRequest(String textMessage){
        this.textMessage = textMessage;
    }

    public String getTextMessage(){
        return this.textMessage;
    }
}
