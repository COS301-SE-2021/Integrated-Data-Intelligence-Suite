package com.Parse_Service.Parse_Service.dataclass;

import javax.persistence.*;

@Entity(name = "parsed_data")
@Table(name = "parsed_data")
public class ParsedData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String textMessage;
    private String date;
    private String location;
    private Integer likes;
    
    public ParsedData() {

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

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
