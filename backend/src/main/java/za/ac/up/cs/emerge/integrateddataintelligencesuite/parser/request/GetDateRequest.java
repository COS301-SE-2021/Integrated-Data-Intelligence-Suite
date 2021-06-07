package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request;

public class GetDateRequest {
    private String jsonString;

    public GetDateRequest(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}