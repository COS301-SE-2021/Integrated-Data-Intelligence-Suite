package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request;

public class GetTextRequest {
    private String jsonString;

    public GetTextRequest(String jsonString) {
        this.jsonString = jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getJsonString() {
        return jsonString;
    }
}