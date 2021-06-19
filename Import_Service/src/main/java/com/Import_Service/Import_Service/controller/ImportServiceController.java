package com.Import_Service.Import_Service.controller;

import com.Import_Service.Import_Service.dataclass.DataSource;
import com.Import_Service.Import_Service.dataclass.ImportedData;
import com.Import_Service.Import_Service.request.ImportDataRequest;
import com.Import_Service.Import_Service.request.ImportTwitterRequest;
import com.Import_Service.Import_Service.response.ImportDataResponse;
import com.Import_Service.Import_Service.response.ImportTwitterResponse;
import com.Import_Service.Import_Service.service.ImportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping(value = "/Import", produces = "application/json")
public class ImportServiceController {

    @Autowired
    private ImportServiceImpl service;

    /*@GetMapping("/{key}")
    public ImportTwitterResponse getTwitterDataJson(@PathVariable String key) throws Exception {
        ImportTwitterRequest req = new ImportTwitterRequest(key);
        return service.getTwitterDataJson(req);
    }*/

    @GetMapping(value = "/importData", produces = "application/json")
    public String importData() throws Exception{
//        //ImportTwitterRequest req = new ImportTwitterRequest(key);
//        ArrayList<ImportedData> list = new ArrayList<>();
//        list.add(new ImportedData(DataSource.TWITTER, "{name: wandile}"));
        String json = "{ \"object\" : \"Json\" }";
        System.out.println(json);
        return "{\"statuses\":[{\"created_at\":\"Fri Jun 18 22:16:52 +0000 2021\",\"id\":1406013066744152076,\"id_str\":\"1406013066744152076\",\"text\":\"@djkaybeedbn @msizi_wandile @kay_mahapa Co-ask\",\"truncated\":false,\"entities\":{\"hashtags\":[],\"symbols\":[],\"user_mentions\":[{\"screen_name\":\"djkaybeedbn\",\"name\":\"Gqom maestro\\ud83c\\udf10\",\"id\":223150660,\"id_str\":\"223150660\",\"indices\":[0,12]},{\"screen_name\":\"msizi_wandile\",\"name\":\"Code IV\",\"id\":1199897803629219840,\"id_str\":\"1199897803629219840\",\"indices\":[13,27]},{\"screen_name\":\"kay_mahapa\",\"name\":\"Lesilo Rula\",\"id\":3352442831,\"id_str\":\"3352442831\",\"indices\":[28,39]}],\"urls\":[]},\"metadata\":{\"iso_language_code\":\"en\",\"result_type\":\"recent\"},\"source\":\"\\u003ca href=\\\"http:\\/\\/twitter.com\\/download\\/android\\\" rel=\\\"nofollow\\\"\\u003eTwitter for Android\\u003c\\/a\\u003e\",\"in_reply_to_status_id\":1406011998547202048,\"in_reply_to_status_id_str\":\"1406011998547202048\",\"in_reply_to_user_id\":223150660,\"in_reply_to_user_id_str\":\"223150660\",\"in_reply_to_screen_name\":\"djkaybeedbn\",\"user\":{\"id\":1281998867433689088,\"id_str\":\"1281998867433689088\",\"name\":\"Zygomaticus\",\"screen_name\":\"Zygomaticus23\",\"location\":\"\",\"description\":\"Kygan \\ud83d\\udc4a\\ud83c\\udfb5\",\"url\":null,\"entities\":{\"description\":{\"urls\":[]}},\"protected\":false,\"followers_count\":294,\"friends_count\":319,\"listed_count\":0,\"created_at\":\"Sat Jul 11 17:09:20 +0000 2020\",\"favourites_count\":3754,\"utc_offset\":null,\"time_zone\":null,\"geo_enabled\":true,\"verified\":false,\"statuses_count\":2203,\"lang\":null,\"contributors_enabled\":false,\"is_translator\":false,\"is_translation_enabled\":false,\"profile_background_color\":\"F5F8FA\",\"profile_background_image_url\":null,\"profile_background_image_url_https\":null,\"profile_background_tile\":false,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/1299498485260189696\\/YTdRbmkH_normal.jpg\",\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/1299498485260189696\\/YTdRbmkH_normal.jpg\",\"profile_link_color\":\"1DA1F2\",\"profile_sidebar_border_color\":\"C0DEED\",\"profile_sidebar_fill_color\":\"DDEEF6\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"has_extended_profile\":false,\"default_profile\":true,\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null,\"translator_type\":\"none\",\"withheld_in_countries\":[]},\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"is_quote_status\":false,\"retweet_count\":0,\"favorite_count\":0,\"favorited\":false,\"retweeted\":false,\"lang\":\"en\"}],\"search_metadata\":{\"completed_in\":0.017,\"max_id\":1406013066744152076,\"max_id_str\":\"1406013066744152076\",\"next_results\":\"?max_id=1406013066744152075&q=wandile&count=1&include_entities=1\",\"query\":\"wandile\",\"refresh_url\":\"?since_id=1406013066744152076&q=wandile&include_entities=1\",\"count\":1,\"since_id\":0,\"since_id_str\":\"0\"}}";
    }

    @GetMapping(value = "/getTwitterDataJson")
    public ImportTwitterResponse getTwitterDataJson(@RequestParam("request") ImportTwitterRequest request) throws Exception {
        //ImportTwitterRequest req = new ImportTwitterRequest(key);
        return service.getTwitterDataJsonx(request);
    }
}
