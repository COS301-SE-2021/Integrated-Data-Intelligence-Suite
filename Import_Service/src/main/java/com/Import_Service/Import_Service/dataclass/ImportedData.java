package com.Import_Service.Import_Service.dataclass;

public class ImportedData {
    private DataSource source;
    private String data;

    public ImportedData(DataSource source, String data) {
        this.source = source;
        this.data = data;
    }

    public ImportedData() {
        source = DataSource.FACEBOOK;
        data="{good :job}";
    }

    public DataSource getSource() {
        return source;
    }

    public String getData() {
        return data;
    }
}
