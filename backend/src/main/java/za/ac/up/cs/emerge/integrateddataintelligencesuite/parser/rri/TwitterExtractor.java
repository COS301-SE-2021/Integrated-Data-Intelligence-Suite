package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.rri;

import org.json.JSONArray;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetDateRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetLikesRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetLocationRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetTextRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetDateResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetLikesResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetLocationResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetTextResponse;
import org.json.JSONObject;

public class TwitterExtractor implements Extractor {
    @Override
    public GetTextResponse getText(GetTextRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("GetTextRequest Object is null");
        }
        if (request.getJsonString() == null){
            throw new InvalidRequestException("Imported string is null");
        }

        String jsonString = request.getJsonString();
        JSONObject obj = new JSONObject(jsonString);
        String responseText = obj.getString("text");
        GetTextResponse response = new GetTextResponse(responseText);
        return response;
    }

    @Override
    public GetDateResponse getDate(GetDateRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("GetDateRequest Object is null");
        }
        if (request.getJsonString() == null){
            throw new InvalidRequestException("Imported string is null");
        }

        String jsonString = request.getJsonString();
        JSONObject obj = new JSONObject(jsonString);
        String[] dateTimeInfo = obj.getString("created_at").split("T");
        String responseDate = dateTimeInfo[0];

        GetDateResponse response = new GetDateResponse(responseDate);
        return response;
    }

    @Override
    public GetLocationResponse getLocation(GetLocationRequest request) throws InvalidRequestException {
        if (request == null || request.getJsonString() == null) {
            throw new InvalidRequestException("The request is invalid");
        }
        String coordinates = "null";

        String jsonString = request.getJsonString();
        JSONObject obj = new JSONObject(jsonString);
        JSONObject geoLocation = obj.getJSONObject("geo");

        if (geoLocation != null) {
            JSONArray coord = geoLocation.getJSONArray("coordinates");
            coordinates = coord.getString(0) + "," + coord.getString(1);
        }

        return new GetLocationResponse(coordinates);
    }

    @Override
    public GetLikesResponse getLikes(GetLikesRequest request) throws InvalidRequestException {
        if (request == null || request.getJsonString() == null) {
            throw new InvalidRequestException("The request is invalid");
        }
        //Set default like to 0
        Integer likes = 0;

        String jsonString = request.getJsonString();
        JSONObject obj = new JSONObject(jsonString);
        likes = obj.getInt("favorite_count");

        return new GetLikesResponse(likes);
    }
}