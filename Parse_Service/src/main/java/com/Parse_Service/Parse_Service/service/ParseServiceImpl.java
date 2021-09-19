package com.Parse_Service.Parse_Service.service;

import com.Parse_Service.Parse_Service.repository.ArticleRepository;
import com.Parse_Service.Parse_Service.repository.DataRepository;
import com.Parse_Service.Parse_Service.rri.ArticleExtractor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Parse_Service.Parse_Service.exception.InvalidRequestException;
import com.Parse_Service.Parse_Service.request.*;
import com.Parse_Service.Parse_Service.response.*;
import com.Parse_Service.Parse_Service.dataclass.*;
import com.Parse_Service.Parse_Service.rri.TwitterExtractor;

import java.util.*;

@Service
public class ParseServiceImpl {

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private ArticleRepository articleRepository;

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
            throw new InvalidRequestException("Request object is null");
        }
        else{
            if (request.getJsonString() == null || request.getJsonString().isEmpty()) {
                throw new InvalidRequestException("Imported string is null");
            }

            if (request.getType() == null){
                throw new InvalidRequestException("Imported type is null");
            }
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
                TwitterExtractor extractor = new TwitterExtractor();

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
                GetLikesResponse likesResponse = extractor.getLikes(likesRequest);
                parsedData.setLikes(likesResponse.getLikes());

                parsedList.add(parsedData);
            }
            //if(request.getPermission().equals("IMPORTING")) {
            //    dataRepository.saveAll(parsedList);
            //}
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
                articleRepository.saveAll(parsedArticlesList);
            }
        }

        return new ParseImportedDataResponse(parsedList, parsedArticlesList);
    }
}
