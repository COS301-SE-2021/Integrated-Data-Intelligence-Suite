package com.Parse_Service.Parse_Service.rri;

import com.Parse_Service.Parse_Service.exception.InvalidRequestException;
import com.Parse_Service.Parse_Service.request.*;
import com.Parse_Service.Parse_Service.response.*;

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

    @Override
    public GetLikesResponse getLikes(GetLikesRequest request) throws InvalidRequestException {
        return null;
    }
}
