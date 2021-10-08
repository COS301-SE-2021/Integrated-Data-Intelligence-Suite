package com.Gateway_Service.Gateway_Service.dataclass.parse;

import com.Gateway_Service.Gateway_Service.rri.DataSource;

public class ParseImportedDataRequest {
    private DataSource type;
    private String jsonString;
    private String permission;
    private String sourceName;

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

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
