package com.Analyse_Service.Analyse_Service.dataclass;

public class ParsedTrainingData {
    private String textMessage;
    private String date;
    private String location;
    private Integer interactions;
    private Integer isTrending;

    public ParsedTrainingData() {

    }

    public ParsedTrainingData(String textMessage, String date, String location, Integer interactions, Integer isTrending) {
        this.textMessage = textMessage;
        this.date = date;
        this.location = location;
        this.interactions = interactions;
        this.isTrending = isTrending;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getInteractions() {
        return interactions;
    }

    public void setInteractions(Integer likes) {
        this.interactions = likes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getIsTrending() {
        return isTrending;
    }

    public void setIsTrending(Integer isTrending) {
        this.isTrending = isTrending;
    }
}
