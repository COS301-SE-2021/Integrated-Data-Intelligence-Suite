package com.Parse_Service.Parse_Service.request;

import com.Parse_Service.Parse_Service.dataclass.DataSource;

public class ParseImportedDataRequest {
    DataSource type;
    String jsonString;

    public ParseImportedDataRequest(DataSource type, String jsonString) {
        this.type = type;
        this.jsonString = jsonString;
    }

    public DataSource getType() {
        return type;
    }

    public void setType(DataSource type) {
        this.type = type;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}