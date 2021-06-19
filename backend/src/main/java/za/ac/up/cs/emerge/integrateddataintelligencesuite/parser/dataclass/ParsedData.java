package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.dataclass;

public class ParsedData {

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
}
