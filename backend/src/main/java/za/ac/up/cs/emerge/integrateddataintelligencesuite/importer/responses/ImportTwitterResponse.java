package za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.responses;

public class ImportTwitterResponse {

    String jsonData;

    public ImportTwitterResponse(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getJsonData() {
        return jsonData;
    }
}
