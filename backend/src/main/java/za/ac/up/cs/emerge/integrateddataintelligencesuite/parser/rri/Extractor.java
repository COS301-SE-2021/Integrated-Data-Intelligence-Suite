package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.rri;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetDateRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetTextRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetDateResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetTextResponse;


public interface Extractor {
    public GetTextResponse getText(GetTextRequest jsonString) throws InvalidRequestException;
    public GetDateResponse getDate(GetDateRequest jsonString) throws InvalidRequestException;
}

