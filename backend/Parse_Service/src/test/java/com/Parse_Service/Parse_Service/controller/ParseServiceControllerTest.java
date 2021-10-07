package com.Parse_Service.Parse_Service.controller;

import com.Parse_Service.Parse_Service.rri.DataSource;
import com.Parse_Service.Parse_Service.dataclass.ParsedArticle;
import com.Parse_Service.Parse_Service.dataclass.ParsedData;
import com.Parse_Service.Parse_Service.exception.InvalidRequestException;
import com.Parse_Service.Parse_Service.request.ParseImportedDataRequest;
import com.Parse_Service.Parse_Service.response.ParseImportedDataResponse;
import com.Parse_Service.Parse_Service.service.ParseServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParseServiceController.class)
public class ParseServiceControllerTest {

    @MockBean
    private ParseServiceImpl service;

    @Autowired
    private MockMvc mockMvc;

    private String mockTwitterData;

    /*@BeforeAll
    public void setup(){
        //this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockTwitterData = "{\n" +
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
    @DisplayName("When_parseImportedData_is_requested")
    public void parseRequest() throws Exception {

    }*/


    @Test
    @DisplayName("When parse check connection")
    public void parseRequestConnection() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/Parse/parseImportedData"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("When parseRequest is Null")
    public void parseDataNullRequest() throws Exception {


        ParseImportedDataRequest parseImportedDataRequest = new ParseImportedDataRequest(DataSource.TWITTER,"{}", "Admin", "Twitter");


        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(parseImportedDataRequest);

        ArrayList<ParsedData> dataList = new ArrayList<>();
        ParsedData parsedData = new ParsedData();
        dataList.add(parsedData);

        ArrayList<ParsedArticle> articleList = new ArrayList<>();
        ParsedArticle parsedArticle = new ParsedArticle();
        articleList.add(parsedArticle);

        ParseImportedDataResponse parseImportedDataResponse = new ParseImportedDataResponse(true, "Parsed imported data", dataList, articleList);
        when(service.parseImportedData(any(ParseImportedDataRequest.class))).thenReturn(parseImportedDataResponse);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Parse/parseImportedData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(results -> Assertions.assertFalse(results.getResolvedException() instanceof InvalidRequestException));

        ParseImportedDataResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), ParseImportedDataResponse.class);

        Assertions.assertNotNull(returnClass);
    }


    @Test
    @DisplayName("When parseRequest is Success")
    public void parseDataSuccessfulRequest() throws Exception {

        ParseImportedDataRequest parseImportedDataRequest = new ParseImportedDataRequest(DataSource.TWITTER,"{}", "Admin", "Twitter");

        ObjectMapper mapper = new ObjectMapper();//new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); //root name of class, same root value of json
        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, true); //increase chances of serializing

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(parseImportedDataRequest);

        ArrayList<ParsedData> dataList = new ArrayList<>();
        ParsedData parsedData = new ParsedData();
        dataList.add(parsedData);

        ArrayList<ParsedArticle> articleList = new ArrayList<>();
        ParsedArticle parsedArticle = new ParsedArticle();
        articleList.add(parsedArticle);

        ParseImportedDataResponse parseImportedDataResponse = new ParseImportedDataResponse(true, "Parsed imported data", dataList, articleList);
        when(service.parseImportedData(any(ParseImportedDataRequest.class))).thenReturn(parseImportedDataResponse);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/Parse/parseImportedData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ParseImportedDataResponse returnClass = mapper.readValue(result.andReturn().getResponse().getContentAsString(), ParseImportedDataResponse.class);

        Assertions.assertNotNull(returnClass);
    }


}
