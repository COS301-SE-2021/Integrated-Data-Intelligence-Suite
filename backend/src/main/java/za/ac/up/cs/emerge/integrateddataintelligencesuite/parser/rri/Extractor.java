package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.rri;

import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetDateRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetLikesRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetLocationRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetTextRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetDateResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetLikesResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetLocationResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetTextResponse;


public interface Extractor {
    GetTextResponse getText(GetTextRequest request) throws InvalidRequestException;
    GetDateResponse getDate(GetDateRequest request) throws InvalidRequestException;
    GetLocationResponse getLocation(GetLocationRequest request) throws InvalidRequestException;
    GetLikesResponse getLikes(GetLikesRequest request) throws  InvalidRequestException;

}

