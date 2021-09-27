package com.Parse_Service.Parse_Service;

import com.Parse_Service.Parse_Service.controller.ParseServiceController;
import com.Parse_Service.Parse_Service.dataclass.NewsProperties;
import com.Parse_Service.Parse_Service.dataclass.ParsedData;
import com.Parse_Service.Parse_Service.dataclass.SocialMediaProperties;
import com.Parse_Service.Parse_Service.repository.NewsPropertiesRepository;
import com.Parse_Service.Parse_Service.repository.SocialMediaPropertiesRepository;
import com.Parse_Service.Parse_Service.request.AddNewsPropertiesRequest;
import com.Parse_Service.Parse_Service.request.AddSocialMediaPropertiesRequest;
import com.Parse_Service.Parse_Service.request.ParseImportedDataRequest;
import com.Parse_Service.Parse_Service.response.AddNewsPropertiesResponse;
import com.Parse_Service.Parse_Service.response.AddSocialMediaPropertiesResponse;
import com.Parse_Service.Parse_Service.response.ParseImportedDataResponse;
import com.Parse_Service.Parse_Service.rri.DataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParseServiceControllerIntegrationTests {
    @Autowired
    private ParseServiceController parseServiceController;

    @Autowired
    private SocialMediaPropertiesRepository socialRepo;

    @Autowired
    private NewsPropertiesRepository newsRepo;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    HttpHeaders requestHeaders = new HttpHeaders();

    private String mockTwitterData;

    private String mockArticleData;

    private final SocialMediaProperties testSocialProps = new SocialMediaProperties("testName", "inter", "loc", "dateMade", "text", "results");

    private final NewsProperties testNewsProps = new NewsProperties("testName2", "inter", "desc", "contente", "text", "results");

    @BeforeAll
    public void init() {
        mockArticleData = "{\n" +
                "  \"status\": \"ok\",\n" +
                "  \"totalResults\": 7166,\n" +
                "  \"articles\": [\n" +
                "    {\n" +
                "      \"source\": {\n" +
                "        \"id\": \"wired\",\n" +
                "        \"name\": \"Wired\"\n" +
                "      },\n" +
                "      \"author\": \"Paul Ford\",\n" +
                "      \"title\": \"A Field Guide for Nature-Resistant Nerds\",\n" +
                "      \"description\": \"Yes, yes, the dirt is horrifying. But it’s also how we make bitcoin apps.\",\n" +
                "      \"url\": \"https://www.wired.com/story/a-field-guide-for-nature-resistant-nerds-microchips-climate-change/\",\n" +
                "      \"urlToImage\": \"https://media.wired.com/photos/61086c497b8c62be3062fe82/191:100/w_1280,c_limit/WI090121_MG_Ford_01.jpg\",\n" +
                "      \"publishedAt\": \"2021-08-06T11:00:00Z\",\n" +
                "      \"content\": \"When my wife started a little garden in our urban backyard, all I could think about were the worms. Also the bugs, and the dirt, which is of course filled with worms and bugs and composted corn cobs.… [+3499 chars]\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        mockTwitterData = "{\n" +
                "    \"statuses\": [\n" +
                "        {\n" +
                "            \"created_at\": \"Sat Jun 19 14:24:09 +0000 2021\",\n" +
                "            \"id\": 1406256491053547529,\n" +
                "            \"id_str\": \"1406256491053547529\",\n" +
                "            \"text\": \"All I can think about is how the #FeesMustFall  movement deserves it's own day of commemoration.\",\n" +
                "            \"truncated\": false,\n" +
                "            \"entities\": {\n" +
                "                \"hashtags\": [\n" +
                "                    {\n" +
                "                        \"text\": \"FeesMustFall\",\n" +
                "                        \"indices\": [\n" +
                "                            52,\n" +
                "                            65\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"symbols\": [],\n" +
                "                \"user_mentions\": [\n" +
                "                    {\n" +
                "                        \"screen_name\": \"LeGhettoSnob_\",\n" +
                "                        \"name\": \"Lerato Khaole\uD83D\uDD4A\uD83D\uDC95\",\n" +
                "                        \"id\": 1352533194378252289,\n" +
                "                        \"id_str\": \"1352533194378252289\",\n" +
                "                        \"indices\": [\n" +
                "                            3,\n" +
                "                            17\n" +
                "                        ]\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"urls\": []\n" +
                "            },\n" +
                "            \"metadata\": {\n" +
                "                \"iso_language_code\": \"en\",\n" +
                "                \"result_type\": \"recent\"\n" +
                "            },\n" +
                "            \"source\": \"<a href=\\\"http://twitter.com/download/android\\\" rel=\\\"nofollow\\\">Twitter for Android</a>\",\n" +
                "            \"in_reply_to_status_id\": null,\n" +
                "            \"in_reply_to_status_id_str\": null,\n" +
                "            \"in_reply_to_user_id\": null,\n" +
                "            \"in_reply_to_user_id_str\": null,\n" +
                "            \"in_reply_to_screen_name\": null,\n" +
                "            \"user\": {\n" +
                "                \"id\": 750230795441373184,\n" +
                "                \"id_str\": \"750230795441373184\",\n" +
                "                \"name\": \"Kabelo Moseamedi\",\n" +
                "                \"screen_name\": \"MoseamediKabelo\",\n" +
                "                \"location\": \"\",\n" +
                "                \"description\": \"\",\n" +
                "                \"url\": null,\n" +
                "                \"entities\": {\n" +
                "                    \"description\": {\n" +
                "                        \"urls\": []\n" +
                "                    }\n" +
                "                },\n" +
                "                \"protected\": false,\n" +
                "                \"followers_count\": 1078,\n" +
                "                \"friends_count\": 2045,\n" +
                "                \"listed_count\": 0,\n" +
                "                \"created_at\": \"Tue Jul 05 07:32:15 +0000 2016\",\n" +
                "                \"favourites_count\": 63773,\n" +
                "                \"utc_offset\": null,\n" +
                "                \"time_zone\": null,\n" +
                "                \"geo_enabled\": false,\n" +
                "                \"verified\": false,\n" +
                "                \"statuses_count\": 24288,\n" +
                "                \"lang\": null,\n" +
                "                \"contributors_enabled\": false,\n" +
                "                \"is_translator\": false,\n" +
                "                \"is_translation_enabled\": false,\n" +
                "                \"profile_background_color\": \"F5F8FA\",\n" +
                "                \"profile_background_image_url\": null,\n" +
                "                \"profile_background_image_url_https\": null,\n" +
                "                \"profile_background_tile\": false,\n" +
                "                \"profile_image_url\": \"http://pbs.twimg.com/profile_images/1291052447104999424/yX8gTWSQ_normal.jpg\",\n" +
                "                \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/1291052447104999424/yX8gTWSQ_normal.jpg\",\n" +
                "                \"profile_banner_url\": \"https://pbs.twimg.com/profile_banners/750230795441373184/1581105578\",\n" +
                "                \"profile_link_color\": \"1DA1F2\",\n" +
                "                \"profile_sidebar_border_color\": \"C0DEED\",\n" +
                "                \"profile_sidebar_fill_color\": \"DDEEF6\",\n" +
                "                \"profile_text_color\": \"333333\",\n" +
                "                \"profile_use_background_image\": true,\n" +
                "                \"has_extended_profile\": true,\n" +
                "                \"default_profile\": true,\n" +
                "                \"default_profile_image\": false,\n" +
                "                \"following\": null,\n" +
                "                \"follow_request_sent\": null,\n" +
                "                \"notifications\": null,\n" +
                "                \"translator_type\": \"none\",\n" +
                "                \"withheld_in_countries\": []\n" +
                "            },\n" +
                "            \"geo\": null,\n" +
                "            \"coordinates\": null,\n" +
                "            \"place\": null,\n" +
                "            \"contributors\": null,\n" +
                "            \"retweeted_status\": {\n" +
                "                \"created_at\": \"Wed Jun 16 09:35:37 +0000 2021\",\n" +
                "                \"id\": 1405096716957794310,\n" +
                "                \"id_str\": \"1405096716957794310\",\n" +
                "                \"text\": \"All I can think about is how the #FeesMustFall  movement deserves it's own day of commemoration.\",\n" +
                "                \"truncated\": false,\n" +
                "                \"entities\": {\n" +
                "                    \"hashtags\": [\n" +
                "                        {\n" +
                "                            \"text\": \"FeesMustFall\",\n" +
                "                            \"indices\": [\n" +
                "                                33,\n" +
                "                                46\n" +
                "                            ]\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"symbols\": [],\n" +
                "                    \"user_mentions\": [],\n" +
                "                    \"urls\": []\n" +
                "                },\n" +
                "                \"metadata\": {\n" +
                "                    \"iso_language_code\": \"en\",\n" +
                "                    \"result_type\": \"recent\"\n" +
                "                },\n" +
                "                \"source\": \"<a href=\\\"http://twitter.com/download/android\\\" rel=\\\"nofollow\\\">Twitter for Android</a>\",\n" +
                "                \"in_reply_to_status_id\": null,\n" +
                "                \"in_reply_to_status_id_str\": null,\n" +
                "                \"in_reply_to_user_id\": null,\n" +
                "                \"in_reply_to_user_id_str\": null,\n" +
                "                \"in_reply_to_screen_name\": null,\n" +
                "                \"user\": {\n" +
                "                    \"id\": 1352533194378252289,\n" +
                "                    \"id_str\": \"1352533194378252289\",\n" +
                "                    \"name\": \"Lerato Khaole\uD83D\uDD4A\uD83D\uDC95\",\n" +
                "                    \"screen_name\": \"LeGhettoSnob_\",\n" +
                "                    \"location\": \"Pretoria, South Africa\",\n" +
                "                    \"description\": \"♤Paid Tweets, I gotta pay the Bill's \uD83D\uDCB0\\n◇Grew up In the Village, moved to CBD\\n♡ Show me mercy tu \uD83D\uDC96\\n♧Bsc. Environmental Science \uD83C\uDF93\",\n" +
                "                    \"url\": null,\n" +
                "                    \"entities\": {\n" +
                "                        \"description\": {\n" +
                "                            \"urls\": []\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"protected\": false,\n" +
                "                    \"followers_count\": 4182,\n" +
                "                    \"friends_count\": 4755,\n" +
                "                    \"listed_count\": 0,\n" +
                "                    \"created_at\": \"Fri Jan 22 08:27:05 +0000 2021\",\n" +
                "                    \"favourites_count\": 16291,\n" +
                "                    \"utc_offset\": null,\n" +
                "                    \"time_zone\": null,\n" +
                "                    \"geo_enabled\": false,\n" +
                "                    \"verified\": false,\n" +
                "                    \"statuses_count\": 2850,\n" +
                "                    \"lang\": null,\n" +
                "                    \"contributors_enabled\": false,\n" +
                "                    \"is_translator\": false,\n" +
                "                    \"is_translation_enabled\": false,\n" +
                "                    \"profile_background_color\": \"F5F8FA\",\n" +
                "                    \"profile_background_image_url\": null,\n" +
                "                    \"profile_background_image_url_https\": null,\n" +
                "                    \"profile_background_tile\": false,\n" +
                "                    \"profile_image_url\": \"http://pbs.twimg.com/profile_images/1380387330301825029/tKetsKy8_normal.jpg\",\n" +
                "                    \"profile_image_url_https\": \"https://pbs.twimg.com/profile_images/1380387330301825029/tKetsKy8_normal.jpg\",\n" +
                "                    \"profile_banner_url\": \"https://pbs.twimg.com/profile_banners/1352533194378252289/1617944926\",\n" +
                "                    \"profile_link_color\": \"1DA1F2\",\n" +
                "                    \"profile_sidebar_border_color\": \"C0DEED\",\n" +
                "                    \"profile_sidebar_fill_color\": \"DDEEF6\",\n" +
                "                    \"profile_text_color\": \"333333\",\n" +
                "                    \"profile_use_background_image\": true,\n" +
                "                    \"has_extended_profile\": true,\n" +
                "                    \"default_profile\": true,\n" +
                "                    \"default_profile_image\": false,\n" +
                "                    \"following\": null,\n" +
                "                    \"follow_request_sent\": null,\n" +
                "                    \"notifications\": null,\n" +
                "                    \"translator_type\": \"none\",\n" +
                "                    \"withheld_in_countries\": []\n" +
                "                },\n" +
                "                \"geo\": null,\n" +
                "                \"coordinates\": null,\n" +
                "                \"place\": null,\n" +
                "                \"contributors\": null,\n" +
                "                \"is_quote_status\": false,\n" +
                "                \"retweet_count\": 5,\n" +
                "                \"favorite_count\": 25,\n" +
                "                \"favorited\": false,\n" +
                "                \"retweeted\": false,\n" +
                "                \"lang\": \"en\"\n" +
                "            },\n" +
                "            \"is_quote_status\": false,\n" +
                "            \"retweet_count\": 5,\n" +
                "            \"favorite_count\": 0,\n" +
                "            \"favorited\": false,\n" +
                "            \"retweeted\": false,\n" +
                "            \"lang\": \"en\"\n" +
                "        }" +
                "    ],\n" +
                "    \"search_metadata\": {\n" +
                "        \"completed_in\": 0.037,\n" +
                "        \"max_id\": 1406256491053547529,\n" +
                "        \"max_id_str\": \"1406256491053547529\",\n" +
                "        \"next_results\": \"?max_id=1406235423601577989&q=feesmustfall&count=3&include_entities=1\",\n" +
                "        \"query\": \"feesmustfall\",\n" +
                "        \"refresh_url\": \"?since_id=1406256491053547529&q=feesmustfall&include_entities=1\",\n" +
                "        \"count\": 3,\n" +
                "        \"since_id\": 0,\n" +
                "        \"since_id_str\": \"0\"\n" +
                "    }\n" +
                "}";
    }

    @AfterAll
    public void remove() {
        Optional<SocialMediaProperties> removeTestSoc = socialRepo.findSocialMediaPropertiesByName(testSocialProps.getName());
        removeTestSoc.ifPresent(socialMediaProperties -> socialRepo.delete(socialMediaProperties));

        Optional<NewsProperties> removeTestNews = newsRepo.findNewsPropertiesByName(testNewsProps.getName());
        removeTestNews.ifPresent(newsProperties -> newsRepo.delete(newsProperties));
    }

    @Test
    @Order(1)
    @DisplayName("Test_To_Ensure_User_Service_Controller_Loads")
    public void testControllerNotNull() {
        Assertions.assertNotNull(parseServiceController);
    }

    @Test
    @Order(2)
    @DisplayName("Test_Parse_Imported_Data_Social_Media_Data")
    public void testParseImportedData() {
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        ParseImportedDataRequest request = new ParseImportedDataRequest(DataSource.TWITTER, mockTwitterData, "VIEWING");

        HttpEntity<ParseImportedDataRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<ParseImportedDataResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/Parse/parseImportedData", HttpMethod.POST, requestEntity, ParseImportedDataResponse.class);
        Assertions.assertNotNull(responseEntity.getBody());
        ArrayList<ParsedData> data = responseEntity.getBody().getDataList();
        Assertions.assertNotNull(data);
    }

    @Test
    @Order(3)
    @DisplayName("Test_Parse_Imported_Data_News_Data")
    public void testParseImportedDataNews() {
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        ParseImportedDataRequest request = new ParseImportedDataRequest(DataSource.NEWSARTICLE, mockArticleData, "VIEWING");

        HttpEntity<ParseImportedDataRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<ParseImportedDataResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/Parse/parseImportedData", HttpMethod.POST, requestEntity, ParseImportedDataResponse.class);
        Assertions.assertNotNull(responseEntity.getBody());
        ArrayList<ParsedData> data = responseEntity.getBody().getDataList();
        Assertions.assertNotNull(data);
    }

    @Test
    @Order(4)
    @DisplayName("Test_Parse_Imported_Data_Social_Media_Data_IMPORTING")
    public void testParseImportedDataSave() {
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        ParseImportedDataRequest request = new ParseImportedDataRequest(DataSource.TWITTER, mockTwitterData, "IMPORTING");

        HttpEntity<ParseImportedDataRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<ParseImportedDataResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/Parse/parseImportedData", HttpMethod.POST, requestEntity, ParseImportedDataResponse.class);
        Assertions.assertNotNull(responseEntity.getBody());
        ArrayList<ParsedData> data = responseEntity.getBody().getDataList();
        Assertions.assertNotNull(data);
    }

    @Test
    @Order(5)
    @DisplayName("Test_Parse_Imported_Data_News_Data_IMPORTING")
    public void testParseImportedDataNewsSave() {
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        ParseImportedDataRequest request = new ParseImportedDataRequest(DataSource.NEWSARTICLE, mockArticleData, "IMPORTING");

        HttpEntity<ParseImportedDataRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<ParseImportedDataResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/Parse/parseImportedData", HttpMethod.POST, requestEntity, ParseImportedDataResponse.class);
        Assertions.assertNotNull(responseEntity.getBody());
        ArrayList<ParsedData> data = responseEntity.getBody().getDataList();
        Assertions.assertNotNull(data);
    }

    @Test
    @Order(6)
    @DisplayName("test_add_social_media_properties")
    public void testAddSocialMediaProps() {
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        AddSocialMediaPropertiesRequest request = new AddSocialMediaPropertiesRequest("testName", "inter", "loc", "dateMade", "text", "results");

        HttpEntity<AddSocialMediaPropertiesRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<AddSocialMediaPropertiesResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/Parse/addSocialMediaProperties", HttpMethod.POST, requestEntity, AddSocialMediaPropertiesResponse.class);
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertTrue(responseEntity.getBody().isSuccess());

        Optional<SocialMediaProperties> exists = socialRepo.findSocialMediaPropertiesByName("testName");
        Assertions.assertTrue(exists.isPresent());
        Assertions.assertEquals(testSocialProps.getName(), exists.get().getName());
    }

    @Test
    @Order(7)
    @DisplayName("test_add_news_properties")
    public void testAddNewsProps() {
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        AddNewsPropertiesRequest request = new AddNewsPropertiesRequest("testName2", "inter", "desc", "contente", "text", "results");

        HttpEntity<AddNewsPropertiesRequest> requestEntity = new HttpEntity<>(request, requestHeaders);
        ResponseEntity<AddNewsPropertiesResponse> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/Parse/addNewsProperties", HttpMethod.POST, requestEntity, AddNewsPropertiesResponse.class);
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertTrue(responseEntity.getBody().isSuccess());

        Optional<NewsProperties> exists = newsRepo.findNewsPropertiesByName(testNewsProps.getName());
        Assertions.assertTrue(exists.isPresent());
        Assertions.assertEquals(testNewsProps.getName(), exists.get().getName());
    }
}
