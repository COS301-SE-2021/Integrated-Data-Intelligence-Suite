package com.Parse_Service.Parse_Service.dataclass;

public class ParsedArticle {
    private String title;
    private String description;
    private String content;
    private String date;

    public ParsedArticle() {

    }

    public ParsedArticle(String title, String description, String content, String date) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String datePublished) {
        this.date = datePublished;
    }
}
