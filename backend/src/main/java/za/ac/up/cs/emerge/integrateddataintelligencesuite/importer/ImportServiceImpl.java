package za.ac.up.cs.emerge.integrateddataintelligencesuite.importer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.hibernate.tool.hbm2ddl.ImportScriptException;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.exceptions.ImporterException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.importer.requests.ImportTwitterRequest;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ImportServiceImpl implements ImportService{
    /**
     *
     * @param request
     * @return
     */
    public String ImportTwitterData(ImportTwitterRequest request) throws ImporterException {

        String keyword = request.getKeyword();

        return "";
    }
    public List<Status> getTwitterData() throws ImporterException {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("zCxJIyRy0WLX9pmcPdzx5azSp")
                .setOAuthConsumerSecret("8B6H1EzSABAWJJp2yRVyGoDnsv8qx2Yvnve2gDvPbT5KA4FuNv")
                .setOAuthAccessToken("1401122362754572291-cG7CQ6KWNq4v53E7FKtWH3VwKPTH2l")
                .setOAuthAccessTokenSecret("IE7PgtFC18GJVKRhl7botuNIM6AtOGyG2rWs5k9sZ9t3R");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        try {
            Query query = new Query("keyword");
            QueryResult result;
            result = twitter.search(query);
            return result.getTweets();

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
            return null;
        }
    }

    public String getTwitterDataJson() throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAANh%2FQQEAAAAAVEkks7xPzGbQL0QGWaHg4z8mNlU%3DIEKW83zjarT23J7vXUX1v5VQH5GBj30wO545Vs9WIOAeRKCXlK")
                .url("https://api.twitter.com/1.1/search/tweets.json?q=wandile&count=10")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        return  Objects.requireNonNull(response.body()).string();
    }

}
