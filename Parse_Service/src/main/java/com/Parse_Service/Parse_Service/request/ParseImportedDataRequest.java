package com.Parse_Service.Parse_Service.request;

import com.Parse_Service.Parse_Service.rri.DataSource;

public class ParseImportedDataRequest {
    DataSource type;
    String jsonString;
    String permission;

    public ParseImportedDataRequest() {

    }

    public ParseImportedDataRequest(DataSource type, String jsonString, String permission) {
        this.type = type;
        this.jsonString = jsonString;
        this.permission = permission;
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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
