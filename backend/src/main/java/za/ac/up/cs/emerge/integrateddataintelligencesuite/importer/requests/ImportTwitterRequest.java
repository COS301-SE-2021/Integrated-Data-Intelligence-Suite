package za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests;

public class ImportTwitterRequest {
    String keyword;

    public ImportTwitterRequest(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
