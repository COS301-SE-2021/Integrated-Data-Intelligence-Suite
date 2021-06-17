package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.rri;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetDateRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetLocationRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetTextRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetDateResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetLocationResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetTextResponse;

public class FacebookExtractor implements Extractor{
    @Override
    public GetTextResponse getText(GetTextRequest jsonString) throws InvalidRequestException {
        return null;
    }

    @Override
    public GetDateResponse getDate(GetDateRequest jsonString) throws InvalidRequestException {
        return null;
    }

    @Override
    public GetLocationResponse getLocation(GetLocationRequest jsonString) throws InvalidRequestException {
        return null;
    }
}
