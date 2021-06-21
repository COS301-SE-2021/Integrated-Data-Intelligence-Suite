package com.Parse_Service.Parse_Service.dataclass;

import javax.persistence.*;

@Entity
@Table
public class ParsedData {
    @Id
    @SequenceGenerator(
            name = "parsedData_sequence",
            sequenceName = "parsedData_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "parsedData_sequence"
    )
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
