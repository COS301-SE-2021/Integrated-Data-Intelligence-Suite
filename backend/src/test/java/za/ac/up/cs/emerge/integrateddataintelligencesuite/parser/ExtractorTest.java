package za.ac.up.cs.emerge.integrateddataintelligencesuite.parser;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.exceptions.InvalidRequestException;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetDateRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.request.GetTextRequest;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetDateResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.response.GetTextResponse;
import za.ac.up.cs.emerge.integrateddataintelligencesuite.parser.rri.TwitterExtractor;

public class ExtractorTest {
    private TwitterExtractor twitterExtractorTest = new TwitterExtractor();

    @Test
    @DisplayName("When the request object of GetText is null")
    public void testExtractorGetTextRequestNUll(){
        //parsingServiceTest = new ParsingServiceImpl();
        Assertions.assertThrows(InvalidRequestException.class, ()->twitterExtractorTest.getText(null));
        
    }

    @Test
    @DisplayName("When the string of GetTextRequest object is null")
    public void testExtractorGetTextJsonStringNUll(){
        //parsingServiceTest = new ParsingServiceImpl();
        GetTextRequest testText = new GetTextRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, ()->twitterExtractorTest.getText(testText));
    }

    @Test
    @DisplayName("When the string of GetTextRequest object is valid")
    public void testExtractorGetTextVaildJsonStringNUll() throws InvalidRequestException {
        //parsingServiceTest = new ParsingServiceImpl();
        GetTextRequest testText = new GetTextRequest("{\"created_at\":\"Sun Jun 06 17:41:19 +0000 2021\",\"id\":1401595070827143176,\"id_str\":\"1401595070827143176\",\"text\":\"@Nkoskhodola_23 Lols he he he I wish we win. Lols u\\u2019ll feel is coz if we get chucked out we\\u2019ll know u too\",\"truncated\":false,\"entities\":{\"hashtags\":[],\"symbols\":[],\"user_mentions\":[{\"screen_name\":\"Nkoskhodola_23\",\"name\":\"Mandisa\\ud83d\\udc99\",\"id\":3422365383,\"id_str\":\"3422365383\",\"indices\":[0,15]}],\"urls\":[]},\"metadata\":{\"iso_language_code\":\"en\",\"result_type\":\"recent\"},\"source\":\"\\u003ca href=\\\"http:\\/\\/twitter.com\\/download\\/iphone\\\" rel=\\\"nofollow\\\"\\u003eTwitter for iPhone\\u003c\\/a\\u003e\",\"in_reply_to_status_id\":1401511558853890055,\"in_reply_to_status_id_str\":\"1401511558853890055\",\"in_reply_to_user_id\":3422365383,\"in_reply_to_user_id_str\":\"3422365383\",\"in_reply_to_screen_name\":\"Nkoskhodola_23\",\"user\":{\"id\":935154685941616641,\"id_str\":\"935154685941616641\",\"name\":\"Gaffit\",\"screen_name\":\"Wandile_Ntini\",\"location\":\"eSwatini, South Africa\",\"description\":\"We Promise To Win\",\"url\":null,\"entities\":{\"description\":{\"urls\":[]}},\"protected\":false,\"followers_count\":3459,\"friends_count\":1306,\"listed_count\":0,\"created_at\":\"Mon Nov 27 14:33:46 +0000 2017\",\"favourites_count\":2305,\"utc_offset\":null,\"time_zone\":null,\"geo_enabled\":false,\"verified\":false,\"statuses_count\":7961,\"lang\":null,\"contributors_enabled\":false,\"is_translator\":false,\"is_translation_enabled\":false,\"profile_background_color\":\"F5F8FA\",\"profile_background_image_url\":null,\"profile_background_image_url_https\":null,\"profile_background_tile\":false,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/1322179791987052544\\/6rEo0oj9_normal.jpg\",\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/1322179791987052544\\/6rEo0oj9_normal.jpg\",\"profile_banner_url\":\"https:\\/\\/pbs.twimg.com\\/profile_banners\\/935154685941616641\\/1563790712\",\"profile_link_color\":\"1DA1F2\",\"profile_sidebar_border_color\":\"C0DEED\",\"profile_sidebar_fill_color\":\"DDEEF6\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"has_extended_profile\":true,\"default_profile\":true,\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null,\"translator_type\":\"none\",\"withheld_in_countries\":[]},\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"is_quote_status\":false,\"retweet_count\":0,\"favorite_count\":1,\"favorited\":false,\"retweeted\":false,\"lang\":\"en\"}");
        GetTextResponse testresp = twitterExtractorTest.getText(testText);
        Assertions.assertNotNull(testresp.getText());
    }

    @Test
    @DisplayName("When the request object of GetDate is null")
    public void testExtractorGetDateRequestNUll(){
        //parsingServiceTest = new ParsingServiceImpl();
        Assertions.assertThrows(InvalidRequestException.class, ()->twitterExtractorTest.getDate(null));
    }

    @Test
    @DisplayName("When the string of GetDateRequest object is null")
    public void testExtractorGetDateJsonStringNUll(){
        //parsingServiceTest = new ParsingServiceImpl();
        GetDateRequest testDate = new GetDateRequest(null);
        Assertions.assertThrows(InvalidRequestException.class, ()->twitterExtractorTest.getDate(testDate));
    }

    @Test
    @DisplayName("When the string of GetDateRequest object is valid")
    public void testExtractorGetDateValidJsonStringNUll() throws InvalidRequestException {
        //parsingServiceTest = new ParsingServiceImpl();
        GetDateRequest testDate = new GetDateRequest("{\"created_at\":\"Sun Jun 06 17:41:19 +0000 2021\",\"id\":1401595070827143176,\"id_str\":\"1401595070827143176\",\"text\":\"@Nkoskhodola_23 Lols he he he I wish we win. Lols u\\u2019ll feel is coz if we get chucked out we\\u2019ll know u too\",\"truncated\":false,\"entities\":{\"hashtags\":[],\"symbols\":[],\"user_mentions\":[{\"screen_name\":\"Nkoskhodola_23\",\"name\":\"Mandisa\\ud83d\\udc99\",\"id\":3422365383,\"id_str\":\"3422365383\",\"indices\":[0,15]}],\"urls\":[]},\"metadata\":{\"iso_language_code\":\"en\",\"result_type\":\"recent\"},\"source\":\"\\u003ca href=\\\"http:\\/\\/twitter.com\\/download\\/iphone\\\" rel=\\\"nofollow\\\"\\u003eTwitter for iPhone\\u003c\\/a\\u003e\",\"in_reply_to_status_id\":1401511558853890055,\"in_reply_to_status_id_str\":\"1401511558853890055\",\"in_reply_to_user_id\":3422365383,\"in_reply_to_user_id_str\":\"3422365383\",\"in_reply_to_screen_name\":\"Nkoskhodola_23\",\"user\":{\"id\":935154685941616641,\"id_str\":\"935154685941616641\",\"name\":\"Gaffit\",\"screen_name\":\"Wandile_Ntini\",\"location\":\"eSwatini, South Africa\",\"description\":\"We Promise To Win\",\"url\":null,\"entities\":{\"description\":{\"urls\":[]}},\"protected\":false,\"followers_count\":3459,\"friends_count\":1306,\"listed_count\":0,\"created_at\":\"Mon Nov 27 14:33:46 +0000 2017\",\"favourites_count\":2305,\"utc_offset\":null,\"time_zone\":null,\"geo_enabled\":false,\"verified\":false,\"statuses_count\":7961,\"lang\":null,\"contributors_enabled\":false,\"is_translator\":false,\"is_translation_enabled\":false,\"profile_background_color\":\"F5F8FA\",\"profile_background_image_url\":null,\"profile_background_image_url_https\":null,\"profile_background_tile\":false,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/1322179791987052544\\/6rEo0oj9_normal.jpg\",\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/1322179791987052544\\/6rEo0oj9_normal.jpg\",\"profile_banner_url\":\"https:\\/\\/pbs.twimg.com\\/profile_banners\\/935154685941616641\\/1563790712\",\"profile_link_color\":\"1DA1F2\",\"profile_sidebar_border_color\":\"C0DEED\",\"profile_sidebar_fill_color\":\"DDEEF6\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"has_extended_profile\":true,\"default_profile\":true,\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null,\"translator_type\":\"none\",\"withheld_in_countries\":[]},\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"is_quote_status\":false,\"retweet_count\":0,\"favorite_count\":1,\"favorited\":false,\"retweeted\":false,\"lang\":\"en\"}");
        GetDateResponse testresp = twitterExtractorTest.getDate(testDate);
        Assertions.assertNotNull(testresp.getDate());
    }
}
