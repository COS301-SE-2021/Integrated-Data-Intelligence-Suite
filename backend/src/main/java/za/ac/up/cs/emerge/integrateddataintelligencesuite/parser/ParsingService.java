package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.ParseImportedDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.ParseImportedDataResponse;

public interface ParsingService {

    ParseImportedDataResponse parseImportedData(ParseImportedDataRequest request);

}
