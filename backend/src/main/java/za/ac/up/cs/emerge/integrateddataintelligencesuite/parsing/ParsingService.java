package za.ac.up.cs.emerge.integrateddataintelligencesuite.parsing;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.parsing.request.ParseImportedDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parsing.response.ParseImportedDataResponse;

public interface ParsingService {

    ParseImportedDataResponse parseImportedData(ParseImportedDataRequest request);

}
