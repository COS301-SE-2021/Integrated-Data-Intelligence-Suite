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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class ImportServiceImpl {
    public ImportTwitterResponse getTwitterDataJson(ImportTwitterRequest req) throws Exception {

        //if(req == null) throw new InvalidTwitterRequestException("request cannot be null");
        //if(req.getKeyword().length() >250 || req.getKeyword().length() < 2) throw new InvalidTwitterRequestException("String length error: string must be between 2 and 250 characters");
        String keyword = req.getKeyword();
        int limit = req.getLimit();

        System.out.println("check here");
        System.out.println(keyword);
        System.out.println(limit);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAANh%2FQQEAAAAAyKF%2BfVbcBjeTIDCyNbQ5pqMiiV8%3DRBtp4v3a4jLMZWOPz84EBLekJCO6JDIeNbkiDdhJhp2CXOQfz7")
                .url("https://api.twitter.com/1.1/search/tweets.json?q="+keyword+"&count="+limit)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        return  new ImportTwitterResponse(Objects.requireNonNull(response.body()).string());
    }

    public ImportDataResponse importData(ImportDataRequest request) throws ImporterException {
        if(request == null) throw new InvalidImporterRequestException("Request object cannot be null");

        if(request.getKeyword().equals("")) throw new InvalidImporterRequestException("Keyword cannot be null");
        if(request.getLimit() <1) throw new InvalidImporterRequestException("Limit cannot be less than 1");
        String keyword = request.getKeyword();
        int limit = request.getLimit();
        ArrayList<ImportedData> list = new ArrayList<>();

        System.out.println(keyword);
        System.out.println(limit);

        try {
            String twitterData = getTwitterDataJson(new ImportTwitterRequest(keyword, limit)).getJsonData();
            list.add(new ImportedData(DataSource.TWITTER, twitterData));
        } catch (Exception e){
            throw new ImporterException("Error while collecting twitter data");
        }

        return new ImportDataResponse(list);

    }
}
