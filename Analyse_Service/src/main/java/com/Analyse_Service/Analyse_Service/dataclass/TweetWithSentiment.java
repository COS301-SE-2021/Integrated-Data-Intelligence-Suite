package com.Analyse_Service.Analyse_Service.dataclass;

public class TweetWithSentiment {
    private String line;
    private String tweet_sentiment;

    public TweetWithSentiment(String line, String tweet_sentiment) {
        super();
        this.line = line;
        this.tweet_sentiment = tweet_sentiment;
    }


    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getCssClass() {
        return tweet_sentiment;
    }

    public void setCssClass(String tweet_sentiment) {
        this.tweet_sentiment = tweet_sentiment;
    }

    @Override
    public String toString() {
        return "TweetWithSentiment [line=" + line + ", Sentiment=" + tweet_sentiment + "]\n";
    }
}
