package com.Gateway_Service.Gateway_Service.dataclass;

public class ImportedData {
    private DataSource source;
    private String data;

    public ImportedData() {

    }

    public ImportedData(DataSource source, String data) {
        this.source = source;
        this.data = data;
    }


    public DataSource getSource() {
        return source;
    }

    public String getData() {
        return data;
    }
}
