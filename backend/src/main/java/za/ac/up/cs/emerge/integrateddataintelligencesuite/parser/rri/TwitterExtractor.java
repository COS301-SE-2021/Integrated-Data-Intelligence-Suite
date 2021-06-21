package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.rri;

import org.json.JSONArray;
import org.json.JSONString;
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
    /**
     * This method is used to extract the text of a given tweet.
     * @param request This is the request class which contains the JSON string
     *                for a particular tweet.
     * @return GetTextResponse This is the response that contains the text of the
     *                         tweet.
     * @throws InvalidRequestException This is thrown if the request or if any of
     *                                 the attributes of the request is null.
     */
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

    /**
     * This method is used to extract the date of a given tweet object.
     * @param request This is the request class which contains the JSON string
     *                for a particular tweet.
     * @return GetTextResponse This is the response object that contains the date
     *                         of the given tweet.
     * @throws InvalidRequestException This is thrown if the request or if any of
     *                                 the attributes of the request is null.
     */
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

    /**
     * This method is used to extract the location of a given tweet object.
     * @param request This is the request class which contains the JSON string
     *                for a particular tweet.
     * @return GetTextResponse This is the response object that contains the date
     *                         of the given tweet.
     * @throws InvalidRequestException This is thrown if the request or if any of
     *                                 the attributes of the request is null.
     */
    @Override
    public GetLocationResponse getLocation(GetLocationRequest request) throws InvalidRequestException {
        if (request == null || request.getJsonString() == null) {
            throw new InvalidRequestException("The request is invalid");
        }
        String coordinates = "not available";

        String jsonString = request.getJsonString();
        //System.out.println(jsonString);
        JSONObject obj = new JSONObject(jsonString);
        //Double c = obj.getJSONArray("geo").getDouble(1);

        return new GetLocationResponse(coordinates);
    }

    /**
     * This method is used to extract the likes of a given tweet object.
     * @param request This is the request class which contains the JSON string
     *                for a particular tweet.
     * @return GetTextResponse This is the response object that contains the likes
     *                         of the given tweet.
     * @throws InvalidRequestException This is thrown if the request or if any of
     *                                 the attributes of the request is null.
     */
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