package com.Parse_Service.Parse_Service.service;

import com.Parse_Service.Parse_Service.dataclass.DataSource;
import com.Parse_Service.Parse_Service.dataclass.ParsedArticle;
import com.Parse_Service.Parse_Service.dataclass.ParsedData;
import com.Parse_Service.Parse_Service.exception.InvalidRequestException;
import com.Parse_Service.Parse_Service.request.ParseImportedDataRequest;
import com.Parse_Service.Parse_Service.response.ParseImportedDataResponse;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
public class ParseServiceTest {
    @Autowired
    private ParseServiceImpl service;

    private String mockJsonData;

    private String mockArticleData;

    @BeforeEach
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

        mockJsonData = "{\n" +
                "    \"statuses\": [\n" +
                "        {\n" +
                "            \"created_at\": \"Sat Jun 19 14:24:09 +0000 2021\",\n" +
                "            \"id\": 1406256491053547529,\n" +
                "            \"id_str\": \"1406256491053547529\",\n" +
                "            \"text\": \"RT @LeGhettoSnob_: All I can think about is how the #FeesMustFall  movement deserves it's own day of commemoration.\",\n" +
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

    @Test
    @DisplayName("If_ParseImportedDataRequest_Is_Null")
    public void parseImportedDataNullRequest() {
        Assertions.assertThrows(InvalidRequestException.class, () -> service.parseImportedData(null));
    }

    @Test
    @DisplayName("If_Request_JSONString_Is_Null")
    public void parseImportedDataJsonStringNull() {
        ParseImportedDataRequest request = new ParseImportedDataRequest(DataSource.TWITTER, null, "");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.parseImportedData(request));
    }

    @Test
    @DisplayName("If_Request_Type_Is_Null")
    public void parseImportedDataTypeNull() {
        ParseImportedDataRequest request = new ParseImportedDataRequest(null, "", "");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.parseImportedData(request));
    }

    @Test
    @DisplayName("Run_With_Mock_Twitter_Data")
    public void parseImportedDataMockTwitterData() throws Exception {
        ParseImportedDataRequest request = new ParseImportedDataRequest(DataSource.TWITTER, mockJsonData, "VIEWING");
        ParseImportedDataResponse response = service.parseImportedData(request);
        ParsedData data = response.getDataList().get(0);
        String expectedText = "RT @LeGhettoSnob_: All I can think about is how the #FeesMustFall  movement deserves it's own day of commemoration.";
        String expectedDate = "Sat Jun 19 14:24:09 +0000 2021";
        Integer likes = 5;
        Assertions.assertEquals(expectedText, data.getTextMessage());
        Assertions.assertEquals(expectedDate, data.getDate());
        Assertions.assertEquals(likes, data.getLikes());
        Assertions.assertNotNull(data.getLocation());
    }

    @Test
    @DisplayName("Test_With_Mock_News_Data")
    public void parseImportedDataMockNewsData() throws Exception {
        ParseImportedDataRequest request = new ParseImportedDataRequest(DataSource.NEWSARTICLE, mockArticleData, "VIEWING");
        ParseImportedDataResponse response = service.parseImportedData(request);
        ParsedArticle data = response.getArticleList().get(0);
        String expectedTitle = "A Field Guide for Nature-Resistant Nerds";
        String expectedDescription = "Yes, yes, the dirt is horrifying. But it’s also how we make bitcoin apps.";
        String expectedDate = "2021-08-06";
        String expectedContent = "When my wife started a little garden in our urban backyard, all I could think about were the worms. Also the bugs, and the dirt, which is of course filled with worms and bugs and composted corn cobs.… [+3499 chars]";
        Assertions.assertEquals(expectedTitle, data.getTitle());
        Assertions.assertEquals(expectedDate, data.getDate());
        Assertions.assertEquals(expectedDescription, data.getDescription());
        Assertions.assertEquals(expectedContent, data.getContent());
    }
}
