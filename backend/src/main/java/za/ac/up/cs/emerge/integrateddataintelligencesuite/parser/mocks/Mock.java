package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.mocks;

public class Mock {

    public Mock(){

    }

    public String getText(){
        return "Hi i'm a message";
    }

    public String getDate(){
        return "12-04-2020";
    }

    public String[] getMention(){
        String[] mentions = {"David","Jeff"};
        return mentions;
    }
}
