package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response;

public class GetLocationResponse {
    private String location;

    public GetLocationResponse(String date) {
        this.location = date;
    }

    public void setLocation(String date) {
        this.location = date;
    }

    public String getLocation() {
        return location;
    }
}
