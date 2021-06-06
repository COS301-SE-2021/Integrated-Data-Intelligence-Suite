package za.ac.up.cs.emerge.integrateddataintelligencesuite.importer;

import org.hibernate.tool.hbm2ddl.ImportScriptException;
import twitter4j.Status;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.exceptions.ImporterException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportTwitterRequest;

import java.io.IOException;
import java.util.List;

public interface ImportService {
    String ImportTwitterData(ImportTwitterRequest request)throws ImporterException;

    List<Status> getTwitterData() throws ImporterException;

    String getTwitterDataJson() throws Exception;
}
