package com.Import_Service.Import_Service.service;

import com.Import_Service.Import_Service.dataclass.DataSource;
import com.Import_Service.Import_Service.dataclass.ImportedData;
import com.Import_Service.Import_Service.exception.ImporterException;
import com.Import_Service.Import_Service.exception.InvalidImporterRequestException;
import com.Import_Service.Import_Service.exception.InvalidTwitterRequestException;
import com.Import_Service.Import_Service.request.ImportDataRequest;
import com.Import_Service.Import_Service.request.ImportTwitterRequest;
import com.Import_Service.Import_Service.response.ImportDataResponse;
import com.Import_Service.Import_Service.response.ImportTwitterResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class ImportServiceImpl {

    @Value("${twitter.bearer}")
    String bearer;

    public ImportTwitterResponse getTwitterDataJson(ImportTwitterRequest req) throws Exception {

        if(req == null) throw new InvalidTwitterRequestException("request cannot be null");

        String keyword = req.getKeyword().strip();
        String token = req.getToken().strip();
        int limit = req.getLimit();

        if(keyword.length() >250 || keyword.length() < 2) throw new InvalidTwitterRequestException("String length error: string must be between 2 and 250 characters");

        if(limit > 100 || limit < 1) throw new InvalidTwitterRequestException("Invalid limit value: limit can only be between 1 and 100");

        if(token.equals("")) throw new InvalidTwitterRequestException("Invalid token: token cannot be empty string ");


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer "+token)
                .url("https://api.twitter.com/1.1/search/tweets.json?q="+keyword+"&count="+limit)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        if(!response.isSuccessful()){
            System.out.println(bearer);
            System.out.println(response.isSuccessful());
            System.out.println(Objects.requireNonNull(response.body()).string());
            throw new ImporterException("Unexpected Error: "+ Objects.requireNonNull(response.body()).string());
        }
        return  new ImportTwitterResponse(Objects.requireNonNull(response.body()).string());
    }

    public ImportDataResponse importData(ImportDataRequest request) throws ImporterException {
        if(request == null) throw new InvalidImporterRequestException("Request object cannot be null");

        if(request.getKeyword().equals("")) throw new InvalidImporterRequestException("Keyword cannot be null");
        if(request.getLimit() <1) throw new InvalidImporterRequestException("Limit cannot be less than 1");
        String keyword = request.getKeyword();
        int limit = request.getLimit();
        ArrayList<ImportedData> list = new ArrayList<>();

//        System.out.println("ImportServiceImpl: "+bearer);

        try {
            String twitterData = getTwitterDataJson(new ImportTwitterRequest(keyword, bearer, limit)).getJsonData();
            System.out.println(twitterData);
            list.add(new ImportedData(DataSource.TWITTER, twitterData));
        } catch (Exception e){
            throw new ImporterException("Error while collecting twitter data");
        }

        return new ImportDataResponse(list);

    }
}
