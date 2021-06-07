package za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.responses;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.ImportedData;

import java.util.ArrayList;

public class ImportDataResponse {

    ArrayList<ImportedData> list;

    public ImportDataResponse(ArrayList<ImportedData> list) {
        this.list = list;
    }

    public ArrayList<ImportedData> getList() {
        return list;
    }
}
