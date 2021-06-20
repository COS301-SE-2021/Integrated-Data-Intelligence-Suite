package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser;


import org.springframework.stereotype.Service;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.ParseImportedDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.ParseImportedDataResponse;

@Service
public interface ParsingService {
    ParseImportedDataResponse parseImportedData(ParseImportedDataRequest request) throws InvalidRequestException;

}
