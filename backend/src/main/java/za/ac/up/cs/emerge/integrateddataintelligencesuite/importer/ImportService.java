package za.ac.up.cs.emerge.integrateddataintelligencesuite.importer;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportTwitterRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.responses.ImportDataResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.responses.ImportTwitterResponse;

import java.util.ArrayList;

public interface ImportService {

    ImportDataResponse importData(ImportDataRequest request) throws Exception;

    ImportTwitterResponse getTwitterDataJson(ImportTwitterRequest req) throws Exception ;
}
