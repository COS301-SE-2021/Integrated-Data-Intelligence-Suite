package com.Analyse_Service.Analyse_Service.dataclass;

import javax.persistence.*;

@Entity
@Table(name = "ParsedData")
public class ParsedData {
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Long ID;

    @Column(name="textMessage")
    private String textMessage;

    @Column(name="date")
    private String date;

    @Column(name="location")
    private String location;

    @Column(name="likes")
    private Integer likes;

    public ParsedData() {

    }

    public Long getID() {
        return ID;
    }

    public void setID(Long id) {
        this.ID = id;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

}
