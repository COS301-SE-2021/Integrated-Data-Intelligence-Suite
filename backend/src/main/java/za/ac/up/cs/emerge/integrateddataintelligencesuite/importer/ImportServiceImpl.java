package za.ac.up.cs.emerge.integrateddataintelligencesuite.importer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.exceptions.*;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportTwitterRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.responses.ImportDataResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.responses.ImportTwitterResponse;

import java.util.ArrayList;
import java.util.Objects;

public class ImportServiceImpl implements ImportService{

    public ImportTwitterResponse getTwitterDataJson(ImportTwitterRequest req) throws Exception {

        if(req == null) throw new InvalidTwitterRequestException("request cannot be null");
        if(req.getKeyword().length() >250 || req.getKeyword().length() < 2) throw new InvalidTwitterRequestException("String length error: string must be between 2 and 250 characters");
        String keyword = req.getKeyword();
        int limit = req.getLimit();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAANh%2FQQEAAAAAVEkks7xPzGbQL0QGWaHg4z8mNlU%3DIEKW83zjarT23J7vXUX1v5VQH5GBj30wO545Vs9WIOAeRKCXlK")
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

        try {
            String twitterData = getTwitterDataJson(new ImportTwitterRequest(keyword, limit)).getJsonData();
            list.add(new ImportedData(DataSource.TWITTER, twitterData));
        } catch (Exception e){
            throw new ImporterException("Error while collecting twitter data");
        }

        return new ImportDataResponse(list);

    }

}
