package com.Parse_Service.Parse_Service.service;

import com.Parse_Service.Parse_Service.exception.ParserException;
import com.Parse_Service.Parse_Service.repository.ArticleRepository;
import com.Parse_Service.Parse_Service.repository.DataRepository;
import com.Parse_Service.Parse_Service.repository.NewsPropertiesRepository;
import com.Parse_Service.Parse_Service.repository.SocialMediaPropertiesRepository;
import com.Parse_Service.Parse_Service.rri.ArticleExtractor;
import com.Parse_Service.Parse_Service.rri.DataSource;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
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

import javax.swing.text.html.parser.Parser;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private static final String[] dateFormats = {
            "yyyy-MM-dd'T'HH:mm:ss'Z'",   "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss",      "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss",
            "MM/dd/yyyy HH:mm:ss",        "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'",
            "dd/MM/yyyy HH:mm:ss",        "dd/MM/yyyy'T'HH:mm:ss.SSS'Z'",
            "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS",
            "dd/MM/yyyy'T'HH:mm:ss.SSSZ", "dd/MM/yyyy'T'HH:mm:ss.SSS",
            "MM/dd/yyyy'T'HH:mm:ssZ",     "MM/dd/yyyy'T'HH:mm:ss",
            "dd/MM/yyyy'T'HH:mm:ssZ",     "dd/MM/yyyy'T'HH:mm:ss",
            "yyyy:MM:dd HH:mm:ss",        "yyyyMMdd", };

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
    public ParseImportedDataResponse parseImportedData(ParseImportedDataRequest request) throws ParserException {
        if (request == null) {
            throw new InvalidRequestException("The request object is null");
        }
        else{
            if (request.getJsonString() == null || request.getJsonString().isEmpty() || request.getType() == null){
                throw new InvalidRequestException("The request contains null values");
            }

            log.info("Parsing imported data");
            System.out.println(request.getJsonString());
            JSONObject obj = new JSONObject(request.getJsonString());
            ArrayList<ParsedData> parsedList = new ArrayList<>();
            ArrayList<ParsedArticle> parsedArticlesList = new ArrayList<>();

            try {
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
            }
            catch (JSONException e) {
                log.info("Failed to parse data: " + e.getMessage());
                throw new ParserException("Failed to parse imported data");
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
     * This function will be used to parse uploaded social media data (mainly CSVs) into the required format
     * for analysis.
     * @param request This class will contain the filename and the necessary columns that will be
     *                used for analysis.
     * @return This is the response that will contain the success value, a message, and a list of
     * parsed data.
     * @throws ParserException This is thrown if there are any errors encountered while parsing.
     */
    public ParseUploadedSocialDataResponse parseUploadedSocialData(ParseUploadedSocialDataRequest request) throws ParserException {
        if(request == null) {
            throw new InvalidRequestException("The request is null");
        }
        else {
            if(request.getFilename() == null || request.getFilename().equals("")) {
                throw new InvalidRequestException("Filename is null or empty");
            }

            if(request.getDateCol() == null && request.getLocCol() == null && request.getInteractionsCol() == null && request.getTextCol()== null) {
                throw new InvalidRequestException("All fields cannot be empty");
            }

            List<String[]> list = new ArrayList<>();
            ArrayList<ParsedData> parsedList = new ArrayList<>();
            try {
                CSVParser parser = new CSVParserBuilder()
                        .withSeparator(',')
                        .withIgnoreQuotations(true)
                        .build();

                CSVReader csvReader = new CSVReaderBuilder(new FileReader(request.getFilename()))
                        .withCSVParser(parser)
                        .build();
                //Check the columns in the uploaded file
                ArrayList<String> columns = new ArrayList<>(Arrays.asList(csvReader.readNext()));

                //Check if the columns exist and get index of required columns. Throw error if they do not exists
                if(columns.contains(request.getDateCol()) && columns.contains(request.getLocCol()) && columns.contains(request.getInteractionsCol()) && columns.contains(request.getTextCol())) {
                    String[] line;
                    while ((line = csvReader.readNext()) != null) {
                        ParsedData dataEntry = new ParsedData();

                        //Set the relevant attributes of the ParsedData type
                        //Parsing date with required formats to test if the date is valid
                        dataEntry.setDate(checkDate(line[columns.indexOf(request.getDateCol())]));
                        dataEntry.setLikes(Integer.parseInt(line[columns.indexOf(request.getInteractionsCol())]));
                        dataEntry.setLocation(line[columns.indexOf(request.getLocCol())]);
                        dataEntry.setTextMessage(line[columns.indexOf(request.getTextCol())]);
                        parsedList.add(dataEntry);
                        System.out.println(dataEntry.getDate());
                        //list.add(line);
                    }
                }
                else {
                    throw new IOException("A column does not exist. Failed to parse data");
                }

                csvReader.close();
            }
            catch (Exception ex) {
                log.error("An error has occurred while parsing: " + ex.getMessage());
                ex.printStackTrace();
                throw new ParserException("An error has occurred trying to parse uploaded social data");
            }

            return new ParseUploadedSocialDataResponse(true, "Successfully parsed uploaded data", parsedList);
        }
    }

    /**
     * This function will be used to parse uploaded social media data (mainly CSVs) into the required format
     * for analysis.
     * @param request This class will contain the filename and the necessary columns that will be
     *                used for analysis.
     * @return This is the response that will contain the success value, a message, and a list of
     * parsed data.
     * @throws ParserException This is thrown if there are any errors encountered while parsing.
     */
    public ParseUploadedNewsDataResponse parseUploadedNewsData(ParseUploadedNewsDataRequest request) throws ParserException {
        if(request == null) {
            throw new InvalidRequestException("The request is null");
        }
        else {
            if(request.getFilename() == null || request.getFilename().equals("")) {
                throw new InvalidRequestException("Filename is null or empty");
            }

            if(request.getDateCol() == null && request.getContentCol() == null && request.getTitleCol() == null && request.getDescCol()== null) {
                throw new InvalidRequestException("All fields cannot be empty");
            }

            List<String[]> list = new ArrayList<>();
            ArrayList<ParsedArticle> articlesList = new ArrayList<>();
            try {
                CSVParser parser = new CSVParserBuilder()
                        .withSeparator(',')
                        .withIgnoreQuotations(true)
                        .build();

                CSVReader csvReader = new CSVReaderBuilder(new FileReader(request.getFilename()))
                        .withCSVParser(parser)
                        .build();
                //Check the columns in the uploaded file
                ArrayList<String> columns = new ArrayList<>(Arrays.asList(csvReader.readNext()));

                //Check if the columns exist and get index of required columns. Throw error if they do not exists
                if(columns.contains(request.getDateCol()) && columns.contains(request.getContentCol()) && columns.contains(request.getTitleCol()) && columns.contains(request.getDescCol())) {
                    String[] line;
                    while ((line = csvReader.readNext()) != null) {
                        ParsedArticle dataEntry = new ParsedArticle();

                        //Set the relevant attributes of the ParsedArticle type
                        //Parsing date with required formats to test if the date is valid
                        dataEntry.setDate(checkDate(line[columns.indexOf(request.getDateCol())]));
                        dataEntry.setContent(line[columns.indexOf(request.getContentCol())]);
                        dataEntry.setDescription(line[columns.indexOf(request.getDescCol())]);
                        dataEntry.setTitle(line[columns.indexOf(request.getTitleCol())]);
                        articlesList.add(dataEntry);
                        //list.add(line);
                    }
                }
                else {
                    throw new IOException("A column does not exist. Failed to parse data");
                }

                csvReader.close();
            }
            catch (Exception ex) {
                log.error("An error has occurred while parsing: " + ex.getMessage());
                ex.printStackTrace();
                throw new ParserException("An error has occurred trying to parse uploaded news data");
            }

            return new ParseUploadedNewsDataResponse(true, "Successfully parsed uploaded data", articlesList);
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

    /**
     * This is a private function to check if the date is within the required format.
     * @param date This is the string that contains the date.
     * @throws IOException This is thrown if the date does not fall within the pre specified formats.
     */
    private String checkDate(String date) throws IOException {
        int numPassed = 0;
        for (String parse : dateFormats) {
            SimpleDateFormat sdf = new SimpleDateFormat(parse);
            try {
                //System.out.println(date);
                Date parsedDate = sdf.parse(date);
                //System.out.println("passed");
                return parsedDate.getDate() + "/" + (parsedDate.getMonth() + 1) + "/" + (parsedDate.getYear() + 1900);
            } catch (ParseException e) {
            }
        }

        throw new IOException("Invalid date format");
    }
}
