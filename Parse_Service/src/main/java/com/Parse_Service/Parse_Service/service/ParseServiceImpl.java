package com.Parse_Service.Parse_Service.service;

import com.Parse_Service.Parse_Service.dataclass.DataSource;
import com.Parse_Service.Parse_Service.dataclass.ParsedData;
import com.Parse_Service.Parse_Service.exception.InvalidRequestException;
import com.Parse_Service.Parse_Service.request.GetDateRequest;
import com.Parse_Service.Parse_Service.request.GetTextRequest;
import com.Parse_Service.Parse_Service.request.ParseImportedDataRequest;
import com.Parse_Service.Parse_Service.response.GetDateResponse;
import com.Parse_Service.Parse_Service.response.GetTextResponse;
import com.Parse_Service.Parse_Service.response.ParseImportedDataResponse;
import com.Parse_Service.Parse_Service.rri.TwitterExtractor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ParseServiceImpl {

    public ParseImportedDataResponse parseImportedData(ParseImportedDataRequest request) throws InvalidRequestException, JSONException {
        if (request == null) {
            throw new InvalidRequestException("ParseImportedDataRequest Object is null");
        }
        else{
            if (request.getJsonString() == null){
                throw new InvalidRequestException("Imported string is null");
            }

            if (request.getType() == null){
                throw new InvalidRequestException("Imported type is null");
            }
        }

        JSONObject obj = new JSONObject(request.getJsonString());
        JSONArray jsonArray = obj.getJSONArray("statuses");;
        ArrayList<ParsedData> newList = new ArrayList<>();

        if (request.getType() == DataSource.TWITTER){
            for (int i=0; i < jsonArray.length(); i++){
                //create and set node
                ParsedData parsedData = new ParsedData();
                TwitterExtractor extractor = new TwitterExtractor();

                //setText
                GetTextRequest textRequest = new GetTextRequest(jsonArray.get(i).toString());
                GetTextResponse textResponse = extractor.getText(textRequest);
                parsedData.setTextMessage(textResponse.getText());

                //setDate
                GetDateRequest dateRequest = new GetDateRequest(jsonArray.get(i).toString());
                GetDateResponse dateResponse = extractor.getDate(dateRequest);
                parsedData.setDate(dateResponse.getDate());

                newList.add(parsedData);
            }
        }

        return new ParseImportedDataResponse(newList);
    }
}
