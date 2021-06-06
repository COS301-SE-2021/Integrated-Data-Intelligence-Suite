package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.dataclass;

public class ParsedData {

    private String textMessage;
    private String[] mention;
    private String date;
    
    public ParsedData() {
        
    }


    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String[] getMention() {
        return mention;
    }

    public void setMention(String[] mention) {
        this.mention = mention;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
