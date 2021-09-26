package com.Parse_Service.Parse_Service.service;

import com.Parse_Service.Parse_Service.repository.ArticleRepository;
import com.Parse_Service.Parse_Service.repository.DataRepository;
import com.Parse_Service.Parse_Service.repository.NewsPropertiesRepository;
import com.Parse_Service.Parse_Service.repository.SocialMediaPropertiesRepository;
import com.Parse_Service.Parse_Service.rri.ArticleExtractor;
import com.Parse_Service.Parse_Service.rri.DataSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Parse_Service.Parse_Service.exception.InvalidRequestException;
import com.Parse_Service.Parse_Service.request.*;
import com.Parse_Service.Parse_Service.response.*;
import com.Parse_Service.Parse_Service.dataclass.*;
import com.Parse_Service.Parse_Service.rri.SocialMediaExtractor;

import java.util.*;

@Service
public class ParseServiceImpl {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private SocialMediaPropertiesRepository socialMediaPropertiesRepository;

    @Autowired
    private NewsPropertiesRepository newsPropertiesRepository;

    private static final Logger log = LoggerFactory.getLogger(ParseServiceImpl.class);

    public ParseServiceImpl() {

    }

    /**
     * This method is used to structure the JSON string sent from the import-service.
     * This method also calls the Extractor interface to extract data based on the source
     * of the data.
     *
     * @param request This is the request object that contains the JSON string.
     * @return ParseImportedDataResponse This class contains structured data after parsing.
     * @throws InvalidRequestException This is thrown if the request or any of it's attributes
     *         are null
     * @throws JSONException This is thrown if the JSONObject or JSONArray does not exist
     *         for a specific key.
     */
    public ParseImportedDataResponse parseImportedData(ParseImportedDataRequest request) throws InvalidRequestException, JSONException {
        if (request == null) {
            throw new InvalidRequestException("The request object is null");
        }
        else{
            if (request.getJsonString() == null || request.getJsonString().isEmpty() || request.getType() == null){
                throw new InvalidRequestException("The request contains null values");
            }

            System.out.println(request.getJsonString());
            JSONObject obj = new JSONObject(request.getJsonString());
            ArrayList<ParsedData> parsedList = new ArrayList<>();
            ArrayList<ParsedArticle> parsedArticlesList = new ArrayList<>();

            if (request.getType() == DataSource.TWITTER) {
                JSONArray jsonArray = obj.getJSONArray("statuses");
                for (int i=0; i < jsonArray.length(); i++){
                    //create and set node
                    ParsedData parsedData = new ParsedData();
                    SocialMediaExtractor extractor = new SocialMediaExtractor();

                    //parse text data from post
                    GetTextRequest textRequest = new GetTextRequest(jsonArray.get(i).toString());
                    GetTextResponse textResponse = extractor.getText(textRequest);
                    parsedData.setTextMessage(textResponse.getText());

                    //parse date data from post
                    GetDateRequest dateRequest = new GetDateRequest(jsonArray.get(i).toString());
                    GetDateResponse dateResponse = extractor.getDate(dateRequest);
                    parsedData.setDate(dateResponse.getDate());

                    //parse location data from post
                    GetLocationRequest locationRequest = new GetLocationRequest(jsonArray.get(i).toString());
                    GetLocationResponse locationResponse = extractor.getLocation(locationRequest);
                    parsedData.setLocation(locationResponse.getLocation());

                    //parse likes from post
                    GetLikesRequest likesRequest = new GetLikesRequest(jsonArray.get(i).toString());
                    GetLikesResponse likesResponse = extractor.getInteractions(likesRequest);
                    parsedData.setLikes(likesResponse.getLikes());

                    parsedList.add(parsedData);
                }
                if(request.getPermission().equals("IMPORTING")) {
                    dataRepository.saveAll(parsedList);
                }
            }
            else if(request.getType() == DataSource.NEWSARTICLE) {
                JSONArray jsonArray = obj.getJSONArray("articles");
                for(int i = 0; i < jsonArray.length() && i < 100; i++) {
                    ParsedArticle article = new ParsedArticle();
                    ArticleExtractor extractor = new ArticleExtractor();

                    //parse title from article
                    article.setTitle(extractor.getTitle(jsonArray.get(i).toString()));

                    //parse description from article
                    article.setDescription(extractor.getDescription(jsonArray.get(i).toString()));

                    //parse description from article
                    article.setContent(extractor.getContent(jsonArray.get(i).toString()));

                    //parse description from article
                    article.setDate(extractor.getDate(jsonArray.get(i).toString()));

                    //add parsed article to list
                    parsedArticlesList.add(article);
                }
                if(request.getPermission().equals("IMPORTING")) {
                    //articleRepository.saveAll(parsedArticlesList);
                }
            }

            if(parsedList.isEmpty() && parsedArticlesList.isEmpty()) {
                log.warn("Failed to parse the imported data");
                return new ParseImportedDataResponse(false, "Failed to parse the imported data", parsedList, parsedArticlesList);
            }
            else {
                log.info("Parsed imported data");
                return new ParseImportedDataResponse(true, "Parsed imported data", parsedList, parsedArticlesList);
            }

        }
    }

    /**
     * This method saved the properties of a particular social media source based
     * on the name of the source.
     * @param request This class contains all the properties necessary to be saved.
     *                - Name
     *                - Interactions property
     *                - Location property
     *                - Date property
     *                - Text property
     *                - Collections property
     * @return This class contains information if the saving of the properties is
     *         successful or not.
     * @throws InvalidRequestException This is thrown if the request is invalid.
     */
    public AddSocialMediaPropertiesResponse addSocialMediaProperties(AddSocialMediaPropertiesRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("The request object is null");
        }
        else {
            if(request.getCollectionProp() == null || request.getDateProp() == null || request.getInteractionsProp() == null || request.getTextProp() == null || request.getLocationProp() == null || request.getName() == null) {
                throw new InvalidRequestException("The request contains null values");
            }

            Optional<SocialMediaProperties> exists = socialMediaPropertiesRepository.findSocialMediaPropertiesByName(request.getName());
            if(exists.isPresent()) {
                return new AddSocialMediaPropertiesResponse(false , "Failed to save properties. Name is taken");
            }

            SocialMediaProperties newProp = new SocialMediaProperties(request.getName(), request.getInteractionsProp(), request.getLocationProp(), request.getDateProp(), request.getTextProp(), request.getCollectionProp());
            SocialMediaProperties saved = socialMediaPropertiesRepository.save(newProp);

            if(newProp == saved) {
                return new AddSocialMediaPropertiesResponse(true, "Successfully saved properties");
            }
            else {
                return new AddSocialMediaPropertiesResponse(false, "Failed to save properties. An error has occurred");
            }

        }
    }

    /**
     * This method saved the properties of a particular news source based
     * on the name of the source.
     * @param request This class contains all the properties necessary to be saved.
     *                - Name
     *                - Interactions property
     *                - Location property
     *                - Date property
     *                - Text property
     *                - Collections property
     * @return This class contains information if the saving of the properties is
     *         successful or not.
     * @throws InvalidRequestException This is thrown if the request is invalid.
     */
    public AddNewsPropertiesResponse addNewsProperties(AddNewsPropertiesRequest request) throws InvalidRequestException {
        if (request == null) {
            throw new InvalidRequestException("The request object is null");
        }
        else {
            if(request.getCollectionProp() == null || request.getDateProp() == null || request.getContentProp() == null || request.getDescriptionProp() == null || request.getTitleProp() == null || request.getName() == null) {
                throw new InvalidRequestException("The request contains null values");
            }

            Optional<NewsProperties> exists = newsPropertiesRepository.findNewsPropertiesByName(request.getName());
            if(exists.isPresent()) {
                return new AddNewsPropertiesResponse(false , "Failed to save properties. Name is taken");
            }

            NewsProperties newProp = new NewsProperties(request.getName(), request.getTitleProp(), request.getDescriptionProp(), request.getContentProp(), request.getDateProp(), request.getCollectionProp());

            NewsProperties saved = newsPropertiesRepository.save(newProp);

            if(newProp == saved) {
                return new AddNewsPropertiesResponse(true, "Successfully saved properties");
            }
            else {
                return new AddNewsPropertiesResponse(false, "Failed to save properties. An error has occurred");
            }

        }
    }
}
