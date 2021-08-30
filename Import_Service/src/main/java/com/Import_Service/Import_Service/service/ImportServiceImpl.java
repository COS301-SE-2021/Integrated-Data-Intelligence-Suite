package com.Import_Service.Import_Service.service;

import com.Import_Service.Import_Service.dataclass.DataSource;
import com.Import_Service.Import_Service.dataclass.ImportedData;
import com.Import_Service.Import_Service.exception.ImporterException;
import com.Import_Service.Import_Service.exception.InvalidImporterRequestException;
import com.Import_Service.Import_Service.exception.InvalidNewsRequestException;
import com.Import_Service.Import_Service.exception.InvalidTwitterRequestException;
import com.Import_Service.Import_Service.request.AddAPISourceRequest;
import com.Import_Service.Import_Service.request.ImportDataRequest;
import com.Import_Service.Import_Service.request.ImportNewsDataRequest;
import com.Import_Service.Import_Service.request.ImportTwitterRequest;
import com.Import_Service.Import_Service.response.AddAPISourceResponse;
import com.Import_Service.Import_Service.response.ImportDataResponse;
import com.Import_Service.Import_Service.response.ImportNewsDataResponse;
import com.Import_Service.Import_Service.response.ImportTwitterResponse;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class ImportServiceImpl {

    @Value("${twitter.bearer}")
    private String bearer;

    @Value("${newsApi.apikey}")
    private String newsToken;

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

            list.add(new ImportedData(DataSource.NEWSSCOURCE, newsData));

        } catch (Exception e) {
            System.out.println("\n\n newsAPI error:"+e.getMessage());
        }

        return new ImportDataResponse(list);
    }

    //====================== Adding API sources ======================

    /**
     * This method will be used to add a new APIs request source to the system.
     * Which will then be used for fetching data from innumerable APIs.
     * @param request This contains all the necessary attributes to store
     *                and use the API call.
     * @return This will return whether or not the adding of a new API source was
     *         successful.
     * @throws Exception This is thrown if the request is null or contains any null values
     */
    public AddAPISourceResponse addAPISource(AddAPISourceRequest request) throws Exception {
        if(request == null) {
            throw new InvalidImporterRequestException("The request cannot be null");
        }
        if(request.getAuthorization() == null || request.getMethod() == null || request.getUrl() == null || request.getParameters() == null) {
            throw new InvalidImporterRequestException("The request cannot contain null attributes");
        }

        return null;
    }
}
