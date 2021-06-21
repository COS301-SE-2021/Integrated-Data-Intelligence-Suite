package com.Parse_Service.Parse_Service.rri;

import com.Parse_Service.Parse_Service.exception.InvalidRequestException;
import com.Parse_Service.Parse_Service.request.GetDateRequest;
import com.Parse_Service.Parse_Service.request.GetTextRequest;
import com.Parse_Service.Parse_Service.response.GetDateResponse;
import com.Parse_Service.Parse_Service.response.GetTextResponse;

public  interface Extractor {
    public GetTextResponse getText(GetTextRequest jsonString) throws InvalidRequestException;
    public GetDateResponse getDate(GetDateRequest jsonString) throws InvalidRequestException;
}
