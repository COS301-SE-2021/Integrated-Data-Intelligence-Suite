package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.ParseImportedDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.ParseImportedDataResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.DataSource;
import java.util.*;

public class ParsingServiceImpl implements ParsingService{

    @Override
    public ParseImportedDataResponse parseImportedData(ParseImportedDataRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("ParseImportedDataRequest Object is null");
        }

        if (request.getJsonString() == null){
            throw new InvalidRequestException("Imported string is null");
        }

        if (request.getType() == null){
            throw new InvalidRequestException("Imported type is null");
        }

        int numTweets = 0;
        if (request.getType() == DataSource.TWITTER){
            for (int i=0; i < numTweets; i++){

            }

        }


        return new ParseImportedDataResponse();
    }
}
