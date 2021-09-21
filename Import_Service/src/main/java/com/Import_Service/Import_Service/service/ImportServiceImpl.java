package com.Import_Service.Import_Service.service;

import com.Import_Service.Import_Service.dataclass.APISource;
import com.Import_Service.Import_Service.rri.AuthorizationType;
import com.Import_Service.Import_Service.rri.DataSource;
import com.Import_Service.Import_Service.dataclass.ImportedData;
import com.Import_Service.Import_Service.exception.ImporterException;
import com.Import_Service.Import_Service.exception.InvalidImporterRequestException;
import com.Import_Service.Import_Service.exception.InvalidNewsRequestException;
import com.Import_Service.Import_Service.exception.InvalidTwitterRequestException;
import com.Import_Service.Import_Service.repository.ApiSourceRepository;
import com.Import_Service.Import_Service.request.*;
import com.Import_Service.Import_Service.response.*;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ImportServiceImpl {

    @Value("${twitter.bearer}")
    private String bearer;

    @Value("${newsApi.apikey}")
    private String newsToken;

    @Autowired
    private ApiSourceRepository apiSourceRepository;

    public ImportServiceImpl() {
    }

    /**
     * This function receives does a get request to twitter using the request parameters given to it
     *
     * @param request a request object specifying different parameter used in creating a request to the twitter API
     * @return  json string representing a list of tweets and its associated information
     * @throws Exception when request object contains invalid parameters or when twitter request
     *                   does not complete successfully
     */
    public ImportTwitterResponse getTwitterDataJson(ImportTwitterRequest request) throws Exception {

        if(request == null){
            throw new InvalidTwitterRequestException("request object is null");
        }

        if(request.getKeyword() == null){
            throw new InvalidTwitterRequestException("Invalid key. key is null");
        }

        if(request.getKeyword().length() >250 || request.getKeyword().length() < 2){
            throw new InvalidTwitterRequestException("String length error: string must be between 2 and 250 characters");
        }

        if(request.getLimit() > 100 || request.getLimit() < 1){
            throw new InvalidTwitterRequestException("Invalid limit value: limit can only be between 1 and 100");
        }

        String keyword = request.getKeyword().strip();
        int limit = request.getLimit();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request req = new Request.Builder()
                .addHeader("Authorization", "Bearer "+bearer)
                .url("https://api.twitter.com/1.1/search/tweets.json?lang=en&q="+keyword+"&count="+limit)
                .method("GET", null)
                .build();
        Response response = client.newCall(req).execute();


        if(!response.isSuccessful()){

            throw new ImporterException("Unexpected Error: "+ Objects.requireNonNull(response.body()).string());
        }
        return  new ImportTwitterResponse(Objects.requireNonNull(response.body()).string());
    }


    /**
     * This function function gets twitter statuses between two specified dates
     *
     * @param request a request object containing the search phrase and two dates which the search should
     *               be between
     * @return an Import Twitter Response object with a String containing a list of statuses.
     * @throws Exception thrown when the request object is null or contains invalid parameters or when
     *                   the Twitter request does not complete successfully
     */
    public ImportTwitterResponse importDatedData(ImportTwitterRequest request) throws Exception {

        if(request == null) {
            throw new InvalidTwitterRequestException("request object is null");
        }

        if(request.getKeyword() == null) {
            throw new InvalidImporterRequestException("Request contains a null value");
        }

        if(request.getKeyword().strip().length() >250 || request.getKeyword().strip().length() < 2) {
            throw new InvalidTwitterRequestException("String length error: string must be between 2 and 250 characters");
        }

        if(request.getFrom() == null) {
            throw new InvalidTwitterRequestException("\"from\" date not specified");
        }

        if(request.getTo() == null) {
            throw new InvalidTwitterRequestException("\"to\" date not specified ");
        }

        LocalDate from = request.getFrom();
        LocalDate to = request.getTo();

        if(from.isAfter(to)) {
            throw new InvalidTwitterRequestException("\"from\" must be earlier than \"to\" date");
        }

        if(from.getYear() < 2006 ){
            throw new InvalidTwitterRequestException("\"from\" date cannot be earlier than 2006");
        }

        if(to.isAfter(LocalDate.now())) {
            throw new InvalidTwitterRequestException("\"to\" date cannot be in the future");
        }

        if(from.isAfter(LocalDate.now())){
            throw new InvalidTwitterRequestException("\"from\" date cannot be in the future");
        }

        String keyword = request.getKeyword().strip();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n   \"query\":\""+ keyword +" lang:en\",\r\n    \"maxResults\": \"100\",\r\n    \"fromDate\":\""+from.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"0000\", \r\n  \"toDate\":\""+ to.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"0000\"\r\n}");
        Request req = new Request.Builder()
                .url("https://api.twitter.com/1.1/tweets/search/fullarchive/IDIS.json")
                .method("POST", body)
                .addHeader("Authorization", "Bearer "+bearer)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(req).execute();
        if(!response.isSuccessful()){
            throw new ImporterException("Unexpected Error: "+ Objects.requireNonNull(response.body()).string());
        }

        if(response.body() == null){
            throw new ImporterException("No data returned");
        }
        return  new ImportTwitterResponse(Objects.requireNonNull(response.body()).string());
    }

    /**
     * this function searches for new articles related to the given search query
     *
     * @param request a request object specifying the parameters to create a request to newsAPi
     * @return a list of articles as specified by the request parameter
     * @throws Exception when request object contains invalid parameters or when newsAPi request
     *                   does not complete successfully
     */
    public ImportNewsDataResponse importNewsData(ImportNewsDataRequest request) throws Exception {

        if(request == null){
            throw new InvalidNewsRequestException("Request object is null.");
        }

        if(request.getKey() == null){
            throw new InvalidNewsRequestException("Invalid key. Key is null");
        }

        if(request.getKey().length() <  3 || request.getKey().length() > 100){
            throw new InvalidNewsRequestException("Invalid key. Key length must be between 3 and 100.");
        }

        if(request.getKey().contains("&") ||  request.getKey().contains("://")){
            throw new InvalidNewsRequestException("Key contain contain :// or &.");
        }

        String key = request.getKey();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request req = new Request.Builder()
                .url("https://newsapi.org/v2/everything?q="+key+"&language=en&apiKey="+newsToken)
                .method("GET", null)
                .build();
        Response response = client.newCall(req).execute();

        if(!response.isSuccessful()){
            throw new ImporterException("Unexpected Error: "+ Objects.requireNonNull(response.body()).string());
        }

        if(response.body() == null){
            throw new ImporterException("No data returned");
        }

        return new ImportNewsDataResponse(Objects.requireNonNull(response.body()).string());
    }

    /**
     * This function searches different data sources based on the search query provided. This
     * function calls importNewsData and getTwitterDataJson and returns their collective results
     *
     * @param request a request object containing a search key and other search related parameters
     * @return a list of data from different data sources related to the search key
     * @throws ImporterException when request object contains invalid parameters or any of the
     *                           data sources does not successfully execute
     */
    public ImportDataResponse importData(ImportDataRequest request) throws ImporterException {

        if(request == null) {
            throw new InvalidImporterRequestException("Request object is null.");
        }
        if(request.getKeyword() == null){
            throw new InvalidImporterRequestException("Keyword is null");
        }
        if(request.getKeyword().length() < 3) {
            throw new InvalidImporterRequestException("Keyword must be between 3 and 100 characters");
        }
        if(request.getLimit() <1) {
            throw new InvalidImporterRequestException("Limit cannot be less than 1.");
        }

        ArrayList<ImportedData> list = new ArrayList<>();

        //Twitter Request
        String keyword = request.getKeyword();
        int limit = request.getLimit();

        try {
            ImportTwitterRequest twitterRequest = new ImportTwitterRequest(keyword, limit);
            ImportTwitterResponse twitterResponse= getTwitterDataJson(twitterRequest);

            String twitterData = twitterResponse.getJsonData();

            list.add(new ImportedData(DataSource.TWITTER, twitterData));

        } catch (Exception e){
            System.out.println("\n\n twitter error: "+e.getMessage());
        }

        //NewsAPI request
        try{
            ImportNewsDataRequest newsRequest = new ImportNewsDataRequest(keyword);
            ImportNewsDataResponse newsResponse = importNewsData(newsRequest);

            String newsData = newsResponse.getData();

            list.add(new ImportedData(DataSource.NEWSARTICLE, newsData));

        } catch (Exception e) {
            System.out.println("\n\n newsAPI error:"+e.getMessage());
        }

        //Fetching api sources from database
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request req;
        List<APISource> sources = apiSourceRepository.findAll();
        /*if(!sources.isEmpty()) {
            for (APISource s : sources) {
                String apiUrl = s.getUrl();
                if (apiUrl.charAt(apiUrl.length() - 1) != '?') {
                    apiUrl += "?";
                }

                apiUrl += s.getSearchKey() + "=" + keyword;

                Map<String, String> params = s.getParameters();

                for (Map.Entry<String, String> entry : params.entrySet()) {
                    apiUrl += "&" + entry.getKey() + "=" + entry.getValue();
                }

                if (s.getAuthType() == AuthorizationType.apiKey) {
                    apiUrl += "&apiKey=" + s.getAuthorization();

                    req = new Request.Builder()
                            .url(apiUrl)
                            .method(s.getMethod(), null)
                            .build();
                } else if (s.getAuthType() == AuthorizationType.bearer) {
                    req = new Request.Builder()
                            .addHeader("Authorization", "Bearer " + s.getAuthorization())
                            .url(apiUrl)
                            .method(s.getMethod(), null)
                            .build();
                } else {
                    req = new Request.Builder()
                            .url(apiUrl)
                            .method(s.getMethod(), null)
                            .build();
                }

                try {
                    Response response = client.newCall(req).execute();

                    if (!response.isSuccessful()) {
                        throw new ImporterException("Unexpected Error: " + Objects.requireNonNull(response.body()).string());
                    }

                    list.add(new ImportedData(DataSource.TWITTER, Objects.requireNonNull(response.body()).string()));
                }
                catch (IOException e) {
                    throw new ImporterException("An error has occurred when executing api call");
                }

            }
        }*/

        return new ImportDataResponse(list);
    }

    //====================== API sources functionality ======================

    /**
     * This method will be used to add a new APIs request source to the system.
     * Which will then be used for fetching data from innumerable APIs.
     * @param request This contains all the necessary attributes to store
     *                and use the API call.
     * @return This will return whether or not the adding of a new API source was
     *         successful.
     * @throws Exception This is thrown if the request is null or contains any null values
     */
    @Transactional
    public AddAPISourceResponse addAPISource(AddAPISourceRequest request) throws Exception {
        if(request == null) {
            throw new InvalidImporterRequestException("The request cannot be null");
        }
        if(request.getAuthorization() == null || request.getMethod() == null || request.getUrl() == null || request.getParameters() == null || request.getName() == null) {
            throw new InvalidImporterRequestException("The request cannot contain null attributes");
        }

        Optional<APISource> findByName = apiSourceRepository.findAPISourceByName(request.getName());

        if(findByName.isPresent()) {
            return new AddAPISourceResponse(false, "The source does not exist");
        }

        APISource newSource = new APISource(request.getName(), request.getUrl(), request.getMethod(), request.getSearch(), request.getAuthType(), request.getAuthorization(), request.getParameters());

        APISource savedSource = apiSourceRepository.save(newSource);

        if(newSource == savedSource) {
            return new AddAPISourceResponse(true, "Successfully saved request");
        }
        else {
            return new AddAPISourceResponse(false, "Failed to save request");
        }
    }

    /**
     * This method will be used to edit an existing API source. The user will be allowed
     * to change all the attributes of a given API source.
     * @param request This class will contain all attributes for an API source including
     *                attributes that haven't been changed.
     * @return This will return whether or not editing an API source was successful.
     * @throws Exception This will be thrown if the request is invalid.
     */
    @Transactional
    public EditAPISourceResponse editAPISource(EditAPISourceRequest request) throws Exception {
        //TODO
        if(request == null) {
            throw new InvalidImporterRequestException("The request cannot be null");
        }
        if(request.getAuthorization() == null || request.getMethod() == null || request.getUrl() == null || request.getParameters() == null || request.getName() == null) {
            throw new InvalidImporterRequestException("The request cannot contain null attributes");
        }

        Optional<APISource> findSource = apiSourceRepository.findById(request.getId());
        if(findSource.isEmpty()) {
            return new EditAPISourceResponse(false, "Source does not exist");
        }
        else {
            APISource source = findSource.get();

            int changedName = apiSourceRepository.updateName(source.getId(), request.getName());
            if(changedName == 0) {
                return new EditAPISourceResponse(false, "Failed to update the name of the API source");
            }

            int changedUrl = apiSourceRepository.updateUrl(source.getId(), request.getUrl());
            if(changedUrl == 0) {
                return new EditAPISourceResponse(false, "Failed to update the URL of the API source");
            }

            int changedAuth = apiSourceRepository.updateAuth(source.getId(), request.getAuthorization());
            if(changedAuth == 0) {
                return new EditAPISourceResponse(false, "Failed to update the authorization code of the API source");
            }

            int changedMethod = apiSourceRepository.updateMethod(source.getId(), request.getMethod());
            if(changedMethod == 0) {
                return new EditAPISourceResponse(false, "Failed to update the HTTP method of the API source");
            }

            int changedType = apiSourceRepository.updateType(source.getId(), request.getAuthType());
            if(changedType == 0) {
                return new EditAPISourceResponse(false, "Failed to update the type of authorization of the API source");
            }

            int changedSearchKey = apiSourceRepository.updateSearchKey(source.getId(), request.getSearchKey());
            if(changedSearchKey == 0) {
                return new EditAPISourceResponse(false, "Failed to update the search key of the API source");
            }

            return new EditAPISourceResponse(true, "Successfully updated the API source");
        }
    }

    /**
     * This method will return a list of all the APISources saved in the database.
     * @return This class contains whether or not the retrieving process was successful
     * or not and the list of APISources
     */
    public GetAllAPISourcesResponse getAllAPISources() {
        List<APISource> sources = apiSourceRepository.findAll();

        if(sources.isEmpty()) {
            return new GetAllAPISourcesResponse(false, "There are no saved API sources", sources);
        }
        else {
            return new GetAllAPISourcesResponse(true, "Successfully retrieved all sources", sources);
        }
    }

    /**
     * This method is used to retrieve a specific API source based on the Id of an API source.
     * @param request This contains the Id of an APISource that is being requested.
     * @return This will return whether or not editing an API source was successful and APISource if it found one.
     * @throws Exception This will be thrown if the request is invalid.
     */
    public GetAPISourceByIdResponse getAPISourceById(GetAPISourceByIdRequest request) throws Exception {
        if(request == null || request.getId() == null) {
            throw new InvalidImporterRequestException("The request is invalid");
        }

        Optional<APISource> source = apiSourceRepository.findById(request.getId());

        if(source.isPresent()) {
            return new GetAPISourceByIdResponse(true, "Retrieved API source", source.get());
        }
        else {
            return new GetAPISourceByIdResponse(false, "Failed to fetch API source", null);
        }
    }
}
