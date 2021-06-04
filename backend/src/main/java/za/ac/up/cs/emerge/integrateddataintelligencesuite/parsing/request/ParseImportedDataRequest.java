package za.ac.up.cs.emerge.integrateddataintelligencesuite.parsing.request;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.importing.DataSource;

public class ParseImportedDataRequest {
    DataSource type;
    String jsonString;

    public ParseImportedDataRequest(DataSource type, String jsonString) {
        this.type = type;
        this.jsonString = jsonString;
    }
}
