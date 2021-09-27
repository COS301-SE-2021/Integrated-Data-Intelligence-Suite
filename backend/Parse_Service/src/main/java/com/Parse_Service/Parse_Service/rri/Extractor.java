package com.Parse_Service.Parse_Service.rri;

import com.Parse_Service.Parse_Service.exception.InvalidRequestException;
import com.Parse_Service.Parse_Service.request.GetDateRequest;
import com.Parse_Service.Parse_Service.request.GetLikesRequest;
import com.Parse_Service.Parse_Service.request.GetLocationRequest;
import com.Parse_Service.Parse_Service.request.GetTextRequest;
import com.Parse_Service.Parse_Service.response.GetDateResponse;
import com.Parse_Service.Parse_Service.response.GetLikesResponse;
import com.Parse_Service.Parse_Service.response.GetLocationResponse;
import com.Parse_Service.Parse_Service.response.GetTextResponse;

public  interface Extractor {
    GetTextResponse getText(GetTextRequest request) throws InvalidRequestException;
    GetDateResponse getDate(GetDateRequest request) throws InvalidRequestException;
    GetLocationResponse getLocation(GetLocationRequest request) throws InvalidRequestException;
    GetLikesResponse getInteractions(GetLikesRequest request) throws  InvalidRequestException;
}
