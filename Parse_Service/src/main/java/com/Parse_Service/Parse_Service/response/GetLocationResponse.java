package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response;

public class GetLocationResponse {
    private String location;

    public GetLocationResponse(String location) {
        this.location = location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
