package com.Parse_Service.Parse_Service.rri;

import com.Parse_Service.Parse_Service.exception.InvalidRequestException;
import org.json.JSONArray;
import org.json.JSONString;
import com.Parse_Service.Parse_Service.request.*;
import com.Parse_Service.Parse_Service.response.*;
import org.json.JSONObject;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialMediaExtractor implements Extractor {
    /**
     * This method is used to extract the text of a given tweet.
     * @param request This is the request class which contains the JSON string
     *                for a particular tweet.
     * @return GetTextResponse This is the response that contains the text of the
     *                         tweet.
     * @throws InvalidRequestException This is thrown if the request or if any of
     *                                 the attributes of the request is null.
     */
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

        String regex = "[^\\p{L}\\p{N}\\p{P}\\p{Z}]";
        Pattern pattern = Pattern.compile(
                regex,
                Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(responseText);
        String text = matcher.replaceAll("");

        GetTextResponse response = new GetTextResponse(text);
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
    public GetDateResponse getDate(GetDateRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("GetDateRequest Object is null");
        }
        if (request.getJsonString() == null){
            throw new InvalidRequestException("Imported string is null");
        }

        String jsonString = request.getJsonString();
        JSONObject obj = new JSONObject(jsonString);
        String dateTimeInfo = obj.getString("created_at");
        String responseDate = dateTimeInfo;

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
    public GetLocationResponse getLocation(GetLocationRequest request) throws InvalidRequestException {
        if (request == null || request.getJsonString() == null) {
            throw new InvalidRequestException("The request is invalid");
        }
        String coordinates = "null";

        String jsonString = request.getJsonString();
        JSONObject obj = new JSONObject(jsonString);

        //Double c = obj.getJSONArray("geo").getDouble(1);
        //Mocking coordinates
        coordinates = generateMockLocation();

        return new GetLocationResponse(coordinates);
    }

    /**
     * This function is used to generate a random latitude and longitude for
     * mocking location data.
     */
    private String generateMockLocation() {
        Random rand = new Random();
        double latitude;
        double longitude;
        int box = rand.nextInt(6 - 1 + 1) + 1;
        switch(box) {
            case 1:
                longitude = rand.nextDouble() * (25.634785 - 18.405492) + 18.405492;
                latitude = rand.nextDouble() * (-28.974683 - (-34.052033)) + (-34.052033);
                break;
            case 2:
                longitude = rand.nextDouble() * (25.430238 - 20.103088) + 20.103088;
                latitude = rand.nextDouble() * (-26.757924 - (-28.974683 )) + (-28.974683 );
                break;
            case 3:
                longitude = rand.nextDouble() * (28.845715 - 25.621856) + 25.621856;
                latitude = rand.nextDouble() * (-29.98297 - (-32.225122)) + (-32.225122);
                break;
            case 4:
                longitude = rand.nextDouble() * (30.987774 - 25.621856) + 25.621856;
                latitude = rand.nextDouble() * (-28.869128 - (-29.944601)) + (-29.944601);
                break;
            case 5:
                longitude = rand.nextDouble() * (32.321107 - 25.621856) + 25.621856;
                latitude = rand.nextDouble() * (-24.966058 - (-28.869128)) + (-28.869128);
                break;
            case 6:
                longitude = rand.nextDouble() * (31.404368 - 27.865276) + 27.865276;
                latitude = rand.nextDouble() * (-22.509154 - (-24.875462)) + (-24.875462);
                break;
            default:
                latitude = 0.0;
                longitude = 0.0;
        }

        return latitude + "," + longitude;
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
    public GetLikesResponse getInteractions(GetLikesRequest request) throws InvalidRequestException {
        if (request == null || request.getJsonString() == null) {
            throw new InvalidRequestException("The request is invalid");
        }
        //Set default like to 0
        Integer likes = 0;

        String jsonString = request.getJsonString();
        JSONObject obj = new JSONObject(jsonString);
        likes = obj.getInt("retweet_count");

        return new GetLikesResponse(likes);
    }
}