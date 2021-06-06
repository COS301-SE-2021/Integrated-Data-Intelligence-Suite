package za.ac.up.cs.emerge.integrateddataintelligencesuite.importer;

public class ImportedData {

    private DataSource source;
    private String data;

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
