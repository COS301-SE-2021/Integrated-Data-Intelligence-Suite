package com.Parse_Service.Parse_Service.rri;

import com.Parse_Service.Parse_Service.exception.InvalidRequestException;
import com.Parse_Service.Parse_Service.request.GetDateRequest;
import com.Parse_Service.Parse_Service.request.GetTextRequest;
import com.Parse_Service.Parse_Service.response.GetDateResponse;
import com.Parse_Service.Parse_Service.response.GetTextResponse;
import org.json.JSONObject;

public class TwitterExtractor implements Extractor {

    public GetTextResponse getText(GetTextRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("GetTextRequest Object is null");
        }
        if (request.getJsonString() == null){
            throw new InvalidRequestException("Imported string is null");
        }

        String jsonString = request.getJsonString();
        JSONObject obj = new JSONObject(jsonString);
        //JSONObject dataObj = obj.getJSONObject("text");
        String responseText = obj.getString("text");
        GetTextResponse response = new GetTextResponse(responseText);
        return response;
    }

    public GetDateResponse getDate(GetDateRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("GetDateRequest Object is null");
        }
        if (request.getJsonString() == null){
            throw new InvalidRequestException("Imported string is null");
        }

        String jsonString = request.getJsonString();
        JSONObject obj = new JSONObject(jsonString);
        //JSONObject dataObj = obj.getJSONObject("statuses");
        String[] dateTimeInfo = obj.getString("created_at").split("T");
        String responseDate = dateTimeInfo[0];

        GetDateResponse response = new GetDateResponse(responseDate);
        return response;
    }
}
