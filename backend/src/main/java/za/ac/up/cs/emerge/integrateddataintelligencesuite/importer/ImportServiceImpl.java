package za.ac.up.cs.emerge.integrateddataintelligencesuite.importer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.exceptions.InvalidKeywordException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.exceptions.InvalidLimitException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportDataRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportTwitterRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.responses.ImportDataResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.responses.ImportTwitterResponse;

import java.util.ArrayList;
import java.util.Objects;

public class ImportServiceImpl implements ImportService{

    public ImportTwitterResponse getTwitterDataJson(ImportTwitterRequest req) throws Exception {

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

    public ImportDataResponse importData(ImportDataRequest request) throws Exception {
        String keyword = request.getKeyword();
        int limit = request.getLimit();
        if(keyword.equals("")) throw new InvalidKeywordException("Keyword cannot be null");
        if(limit <1) throw new InvalidLimitException("Limit cannot be less than 1");

        ArrayList<ImportedData> list = new ArrayList<>();

        try {
            String twitterData = getTwitterDataJson(new ImportTwitterRequest(keyword, limit)).getJsonData();
            list.add(new ImportedData(DataSource.TWITTER, twitterData));
        } catch (Exception e){

        }

        return new ImportDataResponse(list);

    }

}
