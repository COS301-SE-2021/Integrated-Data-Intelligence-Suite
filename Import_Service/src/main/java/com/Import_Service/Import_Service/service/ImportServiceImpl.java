package com.Import_Service.Import_Service.service;

import com.Import_Service.Import_Service.dataclass.DataSource;
import com.Import_Service.Import_Service.dataclass.ImportedData;
import com.Import_Service.Import_Service.exception.ImporterException;
import com.Import_Service.Import_Service.exception.InvalidImporterRequestException;
import com.Import_Service.Import_Service.exception.InvalidNewsRequestException;
import com.Import_Service.Import_Service.exception.InvalidTwitterRequestException;
import com.Import_Service.Import_Service.request.ImportDataRequest;
import com.Import_Service.Import_Service.request.ImportNewsDataRequest;
import com.Import_Service.Import_Service.request.ImportTwitterRequest;
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

    /**
     * @bearer  token string used to authenticate twitter requests
     */
    @Value("${twitter.bearer}")
    String bearer;

    /**
     * @newsToken token string used to authenticate requests to newsAPI
     */
    @Value("${newsApi.apikey}")
    String newsToken;

    public ImportServiceImpl() {
    }

    /**
     *
     * @param request a request object specifying different parameter used in creating a request to the twitter API
     * @return  json string representing a list of tweets and its associated information
     * @throws Exception when request object contains invalid parameters or when twitter
     *                   does not complete successfully
     */
    public ImportTwitterResponse getTwitterDataJson(ImportTwitterRequest request) throws Exception {

        if(request == null) throw new InvalidTwitterRequestException("request cannot be null");

        String keyword = request.getKeyword().strip();
        int limit = request.getLimit();

        if(keyword.length() >250 || keyword.length() < 2) throw new InvalidTwitterRequestException("String length error: string must be between 2 and 250 characters");

        if(limit > 100 || limit < 1) throw new InvalidTwitterRequestException("Invalid limit value: limit can only be between 1 and 100");


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



    public ImportTwitterResponse importDatedData(ImportTwitterRequest request) throws Exception {

        if(request == null) throw new InvalidTwitterRequestException("request object is null");

        if(request.getKeyword().strip().length() >250 || request.getKeyword().strip().length() < 2) throw new InvalidTwitterRequestException("String length error: string must be between 2 and 250 characters");

        if(request.getFrom() == null) throw new InvalidTwitterRequestException("\"from\" date not specified");

        if(request.getTo() == null) throw new InvalidTwitterRequestException("\"to\" date not specified ");

        LocalDate from = request.getFrom();

        LocalDate to = request.getTo();

        if(from.isAfter(to)) throw new InvalidTwitterRequestException("\"from\" must be earlier than \"to\" date");

        if(from.getYear() < 2006 ) throw new InvalidTwitterRequestException("\"from\" date cannot be earlier than 2006");

        if(to.isAfter(LocalDate.now())) throw new InvalidTwitterRequestException("\"to\" date cannot be in the future");

        if(from.isAfter(LocalDate.now())) throw new InvalidTwitterRequestException("\"from\" date cannot be in the future");

        String keyword = request.getKeyword().strip();

        System.out.println("{\r\n   \"query\":\""+ keyword +" lang:en\",\r\n    \"maxResults\": \"100\",\r\n    \"fromDate\":\""+from.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"0000\", \r\n  \"toDate\":\""+ to.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"0000\"\r\n}");

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
        Response res = client.newCall(req).execute();
        if(!res.isSuccessful()){

            throw new ImporterException("Unexpected Error: "+ Objects.requireNonNull(res.body()).string());
        }
        return  new ImportTwitterResponse(Objects.requireNonNull(res.body()).string());
    }

    /**
     *
     * @param request a request object specifying the parameters to create a request to newsAPi
     * @return a list of articles as specified by the request parameter
     * @throws Exception when request object contains invalid parameters or when newsAPi
     *                   does not complete successfully
     */
    public ImportNewsDataResponse importNewsData(ImportNewsDataRequest request) throws Exception {

        if(request == null){
            throw new InvalidNewsRequestException("Request object cannot be null.");
        }

        if(request.getKey() == null){
            throw new InvalidTwitterRequestException("Invalid key. Key length must be between 3 and 100.");
        }

        String key = request.getKey();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request req = new Request.Builder()
                .url("https://newsapi.org/v2/everything?q="+key+"&language=en&apiKey="+newsToken)
                .method("GET", null)
                .build();
        Response response = client.newCall(req).execute();

        System.out.println(response.isSuccessful());
        System.out.println(response.body().string());
        if(!response.isSuccessful()){
            throw new ImporterException("Unexpected Error: "+ Objects.requireNonNull(response.body()).string());

        }

        if(response.body() == null){
            throw new ImporterException("Could not import news data");
        }

//        System.out.println(Objects.requireNonNull(response.body()).string());

        return new ImportNewsDataResponse(Objects.requireNonNull(response.body()).string());
    }

    /**
     *
     * @param request a request object containing a search key and other search related parameters
     * @return a list of data from different data sources related to the search key
     * @throws ImporterException when request object contains invalid parameters or any of the
     *                           data sources does not successfully execute
     */
    public ImportDataResponse importData(ImportDataRequest request) throws ImporterException {

        if(request == null) {
            throw new InvalidImporterRequestException("Request object cannot be null.");
        }
        if(request.getKeyword().equals("")) {
            throw new InvalidImporterRequestException("Keyword cannot be null.");
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

            throw new ImporterException("Error while collecting twitter data.");
        }

        //NewsAPI request

//        try{
//            ImportNewsDataRequest newsRequest = new ImportNewsDataRequest(keyword);
//            ImportNewsDataResponse newsResponse = importNewsData(newsRequest);
//
//            String newsData = newsResponse.getData();
//
//            list.add(new ImportedData(DataSource.NEWSSCOURCE, newsData));
//
//        } catch (Exception e) {
//
//            throw new ImporterException("Error while collecting news data");
//        }
        System.out.println(list.get(0).getData());
        return new ImportDataResponse(list);

    }



}
