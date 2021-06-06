package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response;

public class GetDateResponse {
    private String date;

    public GetDateResponse(String date) {
        this.date = date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}