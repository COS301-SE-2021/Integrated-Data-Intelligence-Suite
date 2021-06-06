package za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.responses;

public class ImportTwitterResponse {

    String jsonData;
//    TODO implement enum class
//    Enum<Platform> Twitter


    public ImportTwitterResponse(String jsonData) {
        this.jsonData = jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
