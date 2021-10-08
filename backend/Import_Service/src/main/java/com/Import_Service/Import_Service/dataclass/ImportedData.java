package com.Import_Service.Import_Service.dataclass;

import com.Import_Service.Import_Service.rri.DataSource;

public class ImportedData {
    private DataSource source;
    private String data;
    private String sourceName;

    public ImportedData() {

    }

    public ImportedData(DataSource source, String data, String sourceName) {
        this.source = source;
        this.data = data;
        this.sourceName = sourceName;
    }

    public DataSource getSource() {
        return source;
    }

    public void setSource(DataSource source) {
        this.source = source;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
