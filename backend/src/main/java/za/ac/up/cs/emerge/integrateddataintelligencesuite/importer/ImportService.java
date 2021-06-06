package za.ac.up.cs.emerge.integrateddataintelligencesuite.importer;

import java.util.ArrayList;

public interface ImportService {

    ArrayList<ImportedData> importData(String keyword, int limit) throws Exception;

    String getTwitterDataJson(String keyword, int limit) throws Exception;
}
